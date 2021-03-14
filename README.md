<h1>NintiChat</h1>

<p>Mod for masking chat messages. Does so by applying masks on a per-user basis using permission arguments.</p>

<h2>Format</h2>

Masks are applied to users or groups with the permissions in the section below.

Masks may contain the standard minecraft formatting tokens [found here](https://minecraft.gamepedia.com/Formatting_codes), as well as the following additional tokens:

* §{name}
    * The username of the player that sent the chat message.
* §{msg}
    * The message the player sent.
* §_
    * A literal space. May be required at the start or end of a mask.

<h2>Permissions</h2>

<h3>`ninti.chat.mask.name`</h3>

Masks usernames.

Example, which is also the default:

`ninti.chat.mask.name: <§{name}>§_` which results in: `<SomeName> Some message.`

<h3>`ninti.chat.mask.msg`</h3>

Masks chat messages, after the username.

Example, which turns text green and wraps it in red quotes:

`ninti.chat.mask.msg: §c"§a§{msg}§c"`

<h2>Requirements</h2>

Requires [NintiCore](https://github.com/c-massie/NintiCore).

Is intended to be run purely server-side.
