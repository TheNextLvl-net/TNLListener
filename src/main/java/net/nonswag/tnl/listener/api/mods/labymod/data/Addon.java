package net.nonswag.tnl.listener.api.mods.labymod.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@ToString
@EqualsAndHashCode
public class Addon implements Cloneable {

    @Getter
    private static final HashMap<String, Addon> addons = new HashMap<>();
    private final String name;
    private final String uniqueId;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private boolean required;

    public Addon(String name, String uniqueId, boolean required) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.required = required;
    }

    public Addon(String name, String uniqueId) {
        this(name, uniqueId, false);
    }

    public Addon register() {
        getAddons().put(getUniqueId(), this);
        return this;
    }

    public Addon unregister() {
        getAddons().remove(getUniqueId());
        return this;
    }

    public boolean isRegistered() {
        return getAddons().containsKey(getUniqueId());
    }

    protected Addon setRequired() {
        this.required = true;
        return this;
    }

    public Addon required() {
        if (isRequired()) return this;
        return clone().setRequired();
    }

    @Override
    protected Addon clone() {
        return new Addon(getName(), getUniqueId());
    }

    @SuppressWarnings("All")
    public static final class v1_16 {
        public static final Addon MINEBOX_COMMUNITY_ADDON = new Addon("MineBox Community Addon", "0c71b393-db39-466d-940b-e94b2472d89a").register();
        public static final Addon LABYTWITTER = new Addon("LabyTwitter 1.16.5", "8f928aca-94f4-40c3-927e-86eeaadd384f").register();
        public static final Addon BETTER_PERSPECTIVE = new Addon("Better Perspective", "aa33e71a-3828-4b98-8dc4-c984a4b9e1e3").register();
        public static final Addon BETTERSCREENSHOT = new Addon("BetterScreenshot", "463d59e2-4cc9-4ab4-9a17-d9c7f523ea01").register();
        public static final Addon GLOBALCHAT = new Addon("GlobalChat", "388c4c0d-5c06-4e15-bcb0-47cb3ab7851e").register();
        public static final Addon LABYTWITCH = new Addon("LabyTwitch", "0222129b-aed5-44ef-80b7-3cd026ea9032").register();
        public static final Addon BETTERTOOLTIPS = new Addon("BetterToolTips", "13ce3743-8ea6-43e9-a0a8-e4c11bee76a6").register();
        public static final Addon VIDEOCHAT = new Addon("VideoChat", "881ca35b-58b1-4c97-80d4-b6adcd059917").register();
        public static final Addon LABYCHATREPLY = new Addon("LabyChatReply 1.16.5", "cd51d18d-2f46-45dc-9f4c-c2794a56a4d6").register();
        public static final Addon OPSUCHT_LABYADDON = new Addon("OPSUCHT LabyAddon", "394ec7d2-ede2-4d10-bc2e-1d7f9859b337").register();
        public static final Addon ADVANCEDAUTOGG = new Addon("AdvancedAutoGG", "9f09b810-abbd-4f8f-9509-8e478b616a13").register();
        public static final Addon SOUNDPLAYER = new Addon("SoundPlayer", "38dd0486-8fb4-45af-bbd4-7f68efca274a").register();
        public static final Addon CHATTIME = new Addon("ChatTime", "cba48d17-fbaa-4245-bb14-d3b882cfdf02").register();
        public static final Addon ANTI_CLEAR_CHAT = new Addon("Anti Clear Chat", "d2770c5d-1733-4bf4-9553-b4e65522a9f4").register();
        public static final Addon NOBOB = new Addon("NoBob", "d97b993d-85a4-4fb5-8b93-904fa8467022").register();
        public static final Addon CUSTOMHITBOXES = new Addon("CustomHitboxes", "de7e9108-aa0a-4637-b81e-0f3317a16660").register();
        public static final Addon AUTORECONNECT = new Addon("AutoReconnect", "e73fcfc9-d41b-47e8-9d2d-e911b837b861").register();
        public static final Addon MOREPARTICLES = new Addon("MoreParticles", "3a289d2b-025b-487c-a096-5aca52fc22ef").register();
        public static final Addon CUSTOM_BLOCK_OVERLAY = new Addon("Custom Block Overlay", "e4c46b18-b5b8-40d5-badc-5f65d6f10c44").register();
        public static final Addon CHATUTILS = new Addon("ChatUtils", "5c7445f2-7a84-40de-9fc1-95164fe66cc5").register();
        public static final Addon FURNITURE = new Addon("Furniture", "bac6b314-75e1-4bba-8837-fe46cfb8c20f").register();
        public static final Addon SPOTIFY = new Addon("Spotify", "cc066c3b-12ff-478f-8391-f57fad6724aa").register();
        public static final Addon WORLDEDIT_IDS_FOR = new Addon("WorldEdit IDs for 1.16", "73b23c4a-2c51-4da6-8716-28635d5dbb38").register();
        public static final Addon SODIUM = new Addon("Sodium", "b556b1f3-e5b7-4df2-a95b-313110a9819e").register();
        public static final Addon HDSKINS = new Addon("HDSkins", "ff6dc051-2878-4452-b15b-3dd9da7ac6a1").register();
        public static final Addon EMOTECHAT = new Addon("EmoteChat", "ad5e9075-1357-43a6-80f0-484945ddf8f7").register();
        public static final Addon ITEMPHYSICS = new Addon("ItemPhysics", "c74ed735-cb16-4921-b3e0-187b17b17a35").register();
        public static final Addon DIRECT_CONNECT_HISTORY = new Addon("Direct Connect History", "e97121db-8af9-4069-80e2-4d185fda5f76").register();
        public static final Addon TNT_TIMER = new Addon("TNT Timer", "0f0ed529-c073-4c2a-bc8b-2c9194fce73a").register();
        public static final Addon FRIEND_TAGS = new Addon("Friend Tags", "c4a4fdb0-4a86-4ea8-8f38-afaef9fd0a48").register();
        public static final Addon OPTIFINE_HD_U_G7 = new Addon("OptiFine 1.16.5 HD U G7", "8d08e37e-c2cd-4fd8-b9e9-7d0a8ab0059c").register();
        public static final Addon CUSTOM_MAIN_MENU = new Addon("Custom Main Menu", "6f1fce7b-5f4e-4284-8a7f-a7f0d2536036").register();
        public static final Addon DAMAGEINDICATOR = new Addon("DamageIndicator", "686fdf14-225d-4475-b062-9f68be19f4d1").register();
        public static final Addon SETTINGS_PROFILE_MOD = new Addon("Settings Profile Mod", "9a1c1d16-c8b9-4113-a2f5-e1e1e25e0ab5").register();
        public static final Addon CUSTOMCROSSHAIR = new Addon("CustomCrosshair", "4bfdd875-960c-497b-aca4-cbcb71de4b21").register();
        public static final Addon LABYS_MINIMAP = new Addon("Laby's Minimap", "6e6f4a6a-f817-4d7e-bfe4-4cc7104d4d38").register();
        public static final Addon RESOURCEPACKS24 = new Addon("Resourcepacks24", "510bc550-38ce-4738-ad11-4f5b63d9c0a3").register();
        public static final Addon RAINBOW_GUI = new Addon("Rainbow GUI", "ba85252b-a452-458a-8c11-cac5e1034773").register();
        public static final Addon TOGGLESNEAK = new Addon("ToggleSneak", "8218ec9a-6ad6-4518-a629-16046f9a24fb").register();
        public static final Addon LABYS_KEYSTROKES = new Addon("Laby's Keystrokes", "19a3fe1c-7187-471b-82ca-ab33f9afddeb").register();
        public static final Addon VOICECHAT = new Addon("VoiceChat", "55c0c094-b79e-4c1b-bfb8-1a9f947c3314").register();
        public static final Addon TEAMSPEAK = new Addon("TeamSpeak", "1ce6f1db-01fa-4d05-ba90-343c953e76b0").register();
    }

    @SuppressWarnings("All")
    public static final class v1_12 {
        public static final Addon GERMANMINERDE = new Addon("GermanMinerDE", "601ff6ce-1b2c-49ed-ba9a-c09ae49b95ac").register();
        public static final Addon TIMOLIA = new Addon("Timolia", "b9392a7e-7694-4994-a70f-4728debf70e8").register();
        public static final Addon CHATTIME = new Addon("ChatTime", "37fb30e9-f984-4e7e-b73b-556dbbd9d7cf").register();
        public static final Addon AUTOGG = new Addon("AutoGG", "8b986a0e-35b7-40cb-a7f3-7675022af394").register();
        public static final Addon CUSTOMHITBOXES = new Addon("CustomHitboxes", "ccb8a00a-7992-4b87-8921-1b19656f113c").register();
        public static final Addon NOBOB = new Addon("NoBob", "b614b841-cc86-4766-a71e-be316fa1ace8").register();
        public static final Addon HDSKINS = new Addon("HDSkins", "dbaf2be9-0762-4480-8015-2682f3aa1e3e").register();
        public static final Addon TNT_TIMER = new Addon("TNT Timer", "0cf09c27-b0a1-4d91-85c7-625f26d10701").register();
        public static final Addon HMAGE = new Addon("HMage", "e2fa8394-55a4-420b-baea-6a62ea081f50").register();
        public static final Addon AUTORECONNECT = new Addon("AutoReconnect", "695bcbd5-3ded-4fe0-a88f-f413712cf087").register();
        public static final Addon EMOTECHAT = new Addon("EmoteChat", "6068f458-3ef0-4add-ae04-c21fac71d783").register();
        public static final Addon GLINT_COLORIZER = new Addon("Glint Colorizer", "11e6f678-01e8-4986-a171-b8c88170c408").register();
        public static final Addon PINGTAG = new Addon("PingTag", "8aaf5651-10e3-4753-a39b-a462504b2300").register();
        public static final Addon FULL_BRIGHT = new Addon("Full Bright", "0291b785-8028-4f88-bb40-f3f2fea6ce91").register();
        public static final Addon I_LOVE_MUSIC = new Addon("I Love Music", "8b6bff84-4266-4e12-89c2-204b142e74fa").register();
        public static final Addon DIRECT_CONNECT_HISTORY = new Addon("Direct Connect History", "772af41d-f4c2-4806-80ee-955afbb3a3f3").register();
        public static final Addon HYPIXEL_QUICK_PLAY = new Addon("Hypixel Quick Play", "c13ee169-0b51-459d-a65e-987f052187c4").register();
        public static final Addon RESOURCEPACKS24 = new Addon("Resourcepacks24", "0705124b-7659-44a2-b008-45f8850b62f8").register();
        public static final Addon CUSTOMCROSSHAIR = new Addon("CustomCrosshair", "5dfc0883-e2fe-4be2-ab5d-e0e6f736ccc2").register();
        public static final Addon REWISSERVER = new Addon("RewisServer", "3bc71387-5bde-48bd-8229-205f70f4aca8").register();
        public static final Addon CUSTOM_MAIN_MENU = new Addon("Custom Main Menu", "c84acd04-07d0-4118-8511-d65b5a6dc99a").register();
        public static final Addon GRIEFERGAMES_ADDON = new Addon("GrieferGames Addon", "4763ca10-13f5-475d-a925-e2214f9ec095").register();
        public static final Addon FRIEND_TAGS = new Addon("Friend Tags", "72115a66-1f9b-4cf0-b346-918075847a7d").register();
        public static final Addon TEAMSPEAK = new Addon("TeamSpeak", "f99fe3da-bc9b-4ff3-8bb1-27765c31aecf").register();
        public static final Addon CUSTOM_BLOCK_OVERLAY = new Addon("Custom Block Overlay", "d33dfb02-da37-4488-b91b-5550bc5b1551").register();
        public static final Addon CUSTOM_FONT = new Addon("Custom Font", "9bbe7717-028c-4230-bf4c-ba7057b04993").register();
        public static final Addon LABYS_KEYSTROKES = new Addon("Laby's Keystrokes", "17981daf-e6ae-44cc-8733-7a68e0e800f6").register();
        public static final Addon SHINYPOTS = new Addon("ShinyPots", "0ab4f35d-6f01-47b0-a972-093352af3a74").register();
        public static final Addon BETTER_PERSPECTIVE = new Addon("Better Perspective", "85b45d21-76eb-44ac-9d1f-75c71f95ebc2").register();
        public static final Addon LABYS_MINIMAP = new Addon("Laby's Minimap", "6289a31b-8914-4142-a77d-b087661e7f0a").register();
        public static final Addon CHATLOG_1_12 = new Addon("ChatLog-1.12", "26bb2788-f55d-4f6e-a37e-504cf73b5941").register();
        public static final Addon VOICECHAT = new Addon("VoiceChat", "24c0644d-ad56-4609-876d-6e9da3cc9794").register();
        public static final Addon CONTROLLER = new Addon("Controller", "e3d90472-5793-4007-8996-f17d4f672725").register();
        public static final Addon SPOTIFY = new Addon("Spotify", "01687f50-baee-4af9-9123-9e1c680f2d9d").register();
        public static final Addon MINIGAMES = new Addon("Minigames", "0119dff5-29e3-4fa4-9104-de06a9e8dfde").register();
        public static final Addon RAINBOW_GUI = new Addon("Rainbow GUI", "2485081f-b50a-47b8-82ed-6aed3952f8ab").register();
        public static final Addon MOTIONBLUR = new Addon("MotionBlur", "00f3ef82-a6a4-47c3-a4df-ae8750dc6a39").register();
        public static final Addon BATTYS_COORDINATES = new Addon("Batty's Coordinates", "b2b04f6d-65d4-4afa-bb5b-a2f2ea27b3b6").register();
        public static final Addon MOREPARTICLES = new Addon("MoreParticles", "7f1a5def-a2c5-40d8-b63c-29ab6eea09ba").register();
        public static final Addon DAMAGEINDICATOR = new Addon("DamageIndicator", "211222f3-2650-4463-b986-e1d29c46117f").register();
        public static final Addon BETTERHAT = new Addon("BetterHat", "b7f0a906-b505-4732-a1fb-0a11a0b1c79c").register();
        public static final Addon WORLDEDITCUI = new Addon("WorldEditCUI", "fee2a522-3769-4af7-bc40-690aa42947ec").register();
        public static final Addon TOGGLESNEAK = new Addon("ToggleSneak", "ba6021d6-bb1a-449b-b59a-680e494a08b8").register();
        public static final Addon GOMMEHDNET = new Addon("GommeHDnet", "ed6ecb1a-7b8d-40bd-be61-0d8893d5e4c5").register();
        public static final Addon SETTINGS_PROFILE_MOD = new Addon("Settings Profile Mod", "c49001df-fe56-4eee-9f92-879603412843").register();
        public static final Addon ITEMPHYSICS = new Addon("ItemPhysics", "355524e5-2b6e-4c43-8d2c-8f80ad4dce6b").register();
        public static final Addon DIRECTIONHUD = new Addon("DirectionHUD", "26d6f912-825a-45f8-877e-97149fb88df8").register();
        public static final Addon OPTIFINE_HD_U_G5 = new Addon("OptiFine 1.12.2 HD U G5", "7d62bffd-fe3f-4667-8200-e8decb384fa0").register();
    }

    @SuppressWarnings("All")
    public static final class v1_8 {
        public static final Addon GLOBALCHAT = new Addon("GlobalChat", "54ab1f45-ea4f-4685-aef0-50f6f46dcc1c").register();
        public static final Addon LABYTWITCH = new Addon("LabyTwitch", "0b4f6956-180f-421c-87e5-2f932add51e9").register();
        public static final Addon TIMOLIA = new Addon("Timolia", "3fb6559d-da96-4dd7-8d19-fe842019b85d").register();
        public static final Addon SUPERPAY = new Addon("SuperPay", "284cdc95-0ff6-4335-a9cb-f7b06e0ba8be").register();
        public static final Addon GRIEFERGAMES = new Addon("GrieferGames", "49233ca8-a752-4ba8-a260-4522b8f4449d").register();
        public static final Addon BADGES = new Addon("Badges", "9d18a620-ccc6-4333-93f2-53a5776f2139").register();
        public static final Addon VIDEOCHAT = new Addon("VideoChat", "cbe8691a-113a-4b24-a109-10378d433bdf").register();
        public static final Addon LABYCHATREPLY = new Addon("LabyChatReply", "d094478b-1034-4f20-b724-6ca5a122397e").register();
        public static final Addon SKYBLOCKADDONS = new Addon("SkyblockAddons", "c3e4dd51-c75e-4ace-b5cf-a9da42ee541d").register();
        public static final Addon CHATTIME = new Addon("ChatTime", "ae639712-c591-4b11-a576-8ebaa8d225f4").register();
        public static final Addon TNT_TIMER = new Addon("TNT Timer", "abd295f8-4483-42d6-8c6e-be845a8e20cb").register();
        public static final Addon CUSTOMHITBOXES = new Addon("CustomHitboxes", "167a11ee-a9d0-4955-a602-8e8f94713add").register();
        public static final Addon NOBOB = new Addon("NoBob", "2f36cb0e-a7d0-42db-8475-dabcc561043e").register();
        public static final Addon SOUNDPLAYER = new Addon("SoundPlayer", "989ee076-ded2-497f-acd4-3c3f7caeec6d").register();
        public static final Addon TRANSLUCENT_SKINS = new Addon("Translucent Skins", "d9468870-331b-4597-8ca9-5b600cd59710").register();
        public static final Addon LABYTIKTOK = new Addon("LabyTikTok", "f7428c84-75e6-496a-87e4-dc5f22759fc8").register();
        public static final Addon CHATCOPPER = new Addon("ChatCopper", "2c2b030c-4cba-420c-94fb-588c6a802ec4").register();
        public static final Addon HDSKINS = new Addon("HDSkins", "b5b78bc3-a2d9-46ac-bbd0-e6236436b346").register();
        public static final Addon FOCUS_MOVEMENT_FIX = new Addon("Focus Movement Fix", "c5eb92ad-bd70-45ef-8a57-ed791cf79bfd").register();
        public static final Addon AUTOGG = new Addon("AutoGG", "a5ac827a-b0ef-46df-b356-247451b6fd10").register();
        public static final Addon AUTORECONNECT = new Addon("AutoReconnect", "e3362096-13b5-49b8-8f4c-7d13083368fd").register();
        public static final Addon BEEZIG___THE_HIVE_SUPPORT = new Addon("Beezig - The Hive Support", "aabbb986-3fbb-4dc8-beea-f0e927d68619").register();
        public static final Addon EMOTECHAT = new Addon("EmoteChat", "8f645692-0be5-4d49-8f51-999a11943017").register();
        public static final Addon GRIEFERGAMES_SCAMMERRADAR = new Addon("GrieferGames ScammerRadar", "5a104c28-8723-4994-9aca-ab3a64e39953").register();
        public static final Addon SEND_TO_SERVER = new Addon("Send To Server", "24d1f048-f85f-4380-9207-0af65fdc43ed").register();
        public static final Addon FRIEND_TAGS = new Addon("Friend Tags", "e11eac53-d2ae-4535-8233-0de32176b196").register();
        public static final Addon GLINT_COLORIZER = new Addon("Glint Colorizer", "388ea126-96f1-415f-8ad3-4131f32f1e66").register();
        public static final Addon INSTANT_TAB_COMPLETION = new Addon("Instant Tab Completion", "20661a70-faae-4459-ac67-b36a73e4fb69").register();
        public static final Addon FULL_BRIGHT = new Addon("Full Bright", "7fdcb1fb-36fb-4851-9991-c765d76ed311").register();
        public static final Addon TOGGLETAB = new Addon("ToggleTab", "0cb59a93-6a59-423f-9172-284f9d3024ff").register();
        public static final Addon I_LOVE_MUSIC = new Addon("I Love Music", "c55895f3-d55c-4be6-8c32-bc6a1fb519b8").register();
        public static final Addon FASTJOIN = new Addon("FastJoin", "5801cf56-18ca-48a3-b2d9-e693d92c57a3").register();
        public static final Addon RANDYMC___SERVER_SUPPORT = new Addon("RandyMC - Server Support", "53cbf3a4-2fd7-4a1a-b8b0-d525366b816a").register();
        public static final Addon CUSTOM_HITBOXES = new Addon("Custom Hitboxes", "da3b53e5-88f9-46a8-94ba-2cf3b4b9c8a8").register();
        public static final Addon DIRECT_CONNECT_HISTORY = new Addon("Direct Connect History", "49d842ca-780a-4ee7-960e-b5a759a73e81").register();
        public static final Addon FRIENDVIEWER = new Addon("FriendViewer", "4dd30326-f696-4c69-b56e-4719da08ea9e").register();
        public static final Addon RESOURCEPACKS24 = new Addon("Resourcepacks24", "9e2b576c-3153-4b1d-a844-c43060c42cdf").register();
        public static final Addon CONNECTIONHISTORY = new Addon("ConnectionHistory", "545e8966-eb5f-4bd3-a975-7ea2af005904").register();
        public static final Addon HYPIXEL_QUICK_PLAY = new Addon("Hypixel Quick Play", "15c4985f-e9a1-4f7f-9dde-57682a60b7bb").register();
        public static final Addon RADIOBOTSEU_LABYMOD_ADDON = new Addon("RadioBotsEU LabyMod Addon", "ef7f3e2d-0cec-4435-aac4-2af84ab32b4a").register();
        public static final Addon REWISSERVER = new Addon("RewisServer", "cb36c6f5-ab0b-4f6d-ba9b-598fb9798397").register();
        public static final Addon HEADOWNER = new Addon("HeadOwner", "41cbf364-c3cd-4328-a2b5-23a4ce5bd265").register();
        public static final Addon CUSTOM_MAIN_MENU = new Addon("Custom Main Menu", "bb3b7bde-7d4e-4b82-a94c-ca1a7c0b5997").register();
        public static final Addon GRIEFERGAMES_ADDON = new Addon("GrieferGames Addon", "fd27e8b8-c25f-4bed-885c-e22683a5c1c8").register();
        public static final Addon ANTI_CLEAR_CHAT = new Addon("Anti Clear Chat", "6bc88ae0-b817-418f-b364-ceb11ca174d6").register();
        public static final Addon CUSTOMFILTERSOUNDS = new Addon("CustomFilterSounds", "818de25e-e31c-4036-a730-ad821dc6ebdb").register();
        public static final Addon CHATTRANSLATOR = new Addon("ChatTranslator", "7a040e25-0713-41af-ab62-7d7b910f606c").register();
        public static final Addon ANTIRAGE = new Addon("AntiRage", "c5a3f0f7-4856-4f0f-bf22-ed20ad27858e").register();
        public static final Addon TEAMSPEAK = new Addon("TeamSpeak", "37efd9cd-0024-4fff-83ac-47beca5cccd8").register();
        public static final Addon CUSTOMTIME = new Addon("CustomTime", "41a83e79-e36c-401e-87bf-cb9164611747").register();
        public static final Addon CUSTOM_BLOCK_OVERLAY = new Addon("Custom Block Overlay", "6d140725-6dcd-4ae9-b401-534a480d214e").register();
        public static final Addon CUSTOM_FONT = new Addon("Custom Font", "325388d5-c435-42bf-83be-d577d816da00").register();
        public static final Addon LABYS_KEYSTROKES = new Addon("Laby's Keystrokes", "00525fd0-eab6-428a-8591-8c3f6eb411fd").register();
        public static final Addon SHINYPOTS = new Addon("ShinyPots", "77be2777-f908-4a3f-863c-2149fd4bd741").register();
        public static final Addon BETTER_PERSPECTIVE = new Addon("Better Perspective", "588d5717-a7f6-4181-9dc8-9980b0a58c0e").register();
        public static final Addon LABYS_MINIMAP = new Addon("Laby's Minimap", "5d77d5b6-bebc-45bf-82f2-336bba64b9b1").register();
        public static final Addon CHATLOG = new Addon("ChatLog", "a44cbd59-2d16-4ace-b8ac-48abe1edc3c4").register();
        public static final Addon BETTERSCREENSHOT = new Addon("BetterScreenshot", "4a183b6d-210a-4f49-a0fe-090f5e842b7b").register();
        public static final Addon VOICECHAT = new Addon("VoiceChat", "43152d5b-ca80-4b29-8f48-39fd63e48dee").register();
        public static final Addon CONTROLLER = new Addon("Controller", "5e692cd6-8e08-4cd2-9a63-58be20f1ea50").register();
        public static final Addon SPOTIFY = new Addon("Spotify", "1160b0a4-9aec-44cb-835b-da2b6cd684a9").register();
        public static final Addon NERUXVACE_NET___SERVERADDON = new Addon("NeruxVace.net - Serveraddon", "cd1dd4e4-9e06-401d-919f-3096f04d179e").register();
        public static final Addon LABYINSTAGRAM = new Addon("LabyInstagram", "d0ed1f6a-2811-4113-819f-495078d23915").register();
        public static final Addon HOTBAR_ = new Addon("Hotbar ", "0846893c-e926-470c-bffa-72b2d96935a1").register();
        public static final Addon BAUSUCHT_NET_SERVERSUPPORT = new Addon("Bausucht.net Serversupport", "ec0bb840-7e3d-4f0d-87b4-62c5ea268685").register();
        public static final Addon GUI_BLUR_MOD = new Addon("GUI Blur Mod", "692dccfe-02d1-41ce-b688-690cdff1e2b7").register();
        public static final Addon LABYTWITTER = new Addon("LabyTwitter", "b57b064e-5eb6-441b-92d8-5dd6d2bfbd58").register();
        public static final Addon MINIGAMES = new Addon("Minigames", "a67fd3ed-6a66-4a1e-8cfd-cba6ebd8576c").register();
        public static final Addon RAINBOW_GUI = new Addon("Rainbow GUI", "376936ee-9dd3-476e-be84-4147dbc17baf").register();
        public static final Addon COLOR_CORRECTION = new Addon("Color Correction", "53b3d90c-6e56-4602-9d61-2d3a35574167").register();
        public static final Addon MOTIONBLUR = new Addon("MotionBlur", "3f18b7d9-04ee-4e9d-947d-ebe38b7c84d2").register();
        public static final Addon BATTYS_COORDINATES = new Addon("Batty's Coordinates", "b89e2567-090f-4c8d-ad7c-d7d54eebe2a0").register();
        public static final Addon CUSTOMCROSSHAIR = new Addon("CustomCrosshair", "617c0013-b1c0-44b6-93af-86ab6720ed92").register();
        public static final Addon MOREPARTICLES = new Addon("MoreParticles", "de053e2e-7a28-47bc-9d50-09ba2348a551").register();
        public static final Addon DAMAGEINDICATOR = new Addon("DamageIndicator", "8d4ed1eb-4b1c-46bc-aac9-8abc8c6a1308").register();
        public static final Addon BETTERHAT = new Addon("BetterHat", "686b5a9f-0cd4-4bd6-a230-08771fa206e8").register();
        public static final Addon GOMMEHDNET = new Addon("GommeHDnet", "28f017b0-2362-448a-8a9b-75203e20b920").register();
        public static final Addon WORLDEDITCUI = new Addon("WorldEditCUI", "c9853888-8536-11e7-bb31-be2e44b06b34").register();
        public static final Addon DIRECTIONHUD = new Addon("DirectionHUD", "5dc6e650-8536-11e7-bb31-be2e44b06b34").register();
        public static final Addon TOGGLESNEAK = new Addon("ToggleSneak", "a71154bc-8536-11e7-bb31-be2e44b06b34").register();
        public static final Addon ITEMPHYSICS = new Addon("ItemPhysics", "8a7b096a-8536-11e7-bb31-be2e44b06b34").register();
        public static final Addon SETTINGS_PROFILE_MOD = new Addon("Settings Profile Mod", "df1be19c-8536-11e7-bb31-be2e44b06b34").register();
        public static final Addon OPTIFINE_HD_U_M5 = new Addon("OptiFine 1.8.9 HD U M5", "2cc09032-995f-4b57-a2a1-f1399addbb21").register();
    }
}
