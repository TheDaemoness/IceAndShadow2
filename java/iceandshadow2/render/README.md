Contains exclusively client-side code.
This means renderers, but also particle effects.

Most of this classes in this package should be annotated with `@SideOnly(Side.CLIENT)`.

Some classes in this package may make it to server-side code, but in that event they should do nothing.
