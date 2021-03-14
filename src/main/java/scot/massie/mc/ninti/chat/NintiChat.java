package scot.massie.mc.ninti.chat;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scot.massie.lib.utils.StringUtils;
import scot.massie.mc.ninti.core.Permissions;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("nintichat")
public class NintiChat
{
    public static final String PERMISSION_MASK_NAME = "ninti.chat.mask.name";
    public static final String PERMISSION_MASK_MSG = "ninti.chat.mask.msg";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public NintiChat()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
        Permissions.Suggestions.add(PERMISSION_MASK_NAME, PERMISSION_MASK_MSG);
    }

    @SubscribeEvent
    public void onChat(final ServerChatEvent event)
    {
        String namePattern = Permissions.getPlayerPermissionArg(event.getPlayer(), PERMISSION_MASK_NAME);
        String msgPattern = Permissions.getPlayerPermissionArg(event.getPlayer(), PERMISSION_MASK_MSG);

        if(namePattern == null && msgPattern == null)
            return;

        if(namePattern == null)
            namePattern = "<§{name}>§_";

        if(msgPattern == null)
            msgPattern = "§{msg}";

        namePattern = maskChatLinePart(namePattern, event.getUsername(), event.getMessage());
        msgPattern  = maskChatLinePart(msgPattern, event.getUsername(), event.getMessage());

        event.setComponent(new StringTextComponent(namePattern + msgPattern));
    }

    public String maskChatLinePart(String chatLinePart, final String username, final String msg)
    {
        chatLinePart = chatLinePart.replaceAll("§\\{name}", username);
        chatLinePart = chatLinePart.replaceAll("§\\{msg}", msg);
        chatLinePart = chatLinePart.replaceAll("§_", " ");
        return chatLinePart;
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

    }
}
