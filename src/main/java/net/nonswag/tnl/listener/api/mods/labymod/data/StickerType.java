package net.nonswag.tnl.listener.api.mods.labymod.data;

import javax.annotation.Nonnull;

public record StickerType(@Nonnull String name, int id) {

    public static final class HalloweenPack {

        @Nonnull
        public static final StickerType PUMPKIN = new StickerType("Pumpkin", 1);
        @Nonnull
        public static final StickerType HAPPY_HALLOWEEN = new StickerType("Happy Halloween", 2);
        @Nonnull
        public static final StickerType TRICK_OR_TREAT = new StickerType("Trick or treat", 3);
        @Nonnull
        public static final StickerType BOO = new StickerType("Boo", 4);
        @Nonnull
        public static final StickerType GHOST = new StickerType("Ghost", 5);
    }

    public static final class ChristmasPack {

        @Nonnull
        public static final StickerType SANTA = new StickerType("Santa", 6);
        @Nonnull
        public static final StickerType REINDEER = new StickerType("Reindeer", 7);
        @Nonnull
        public static final StickerType MERRY_CHRISTMAS = new StickerType("Merry Christmas", 8);
        @Nonnull
        public static final StickerType HOHOHO = new StickerType("Hohoho", 9);
        @Nonnull
        public static final StickerType CHRISTMAS_BALL = new StickerType("Christmas Ball", 10);
    }

    public static final class RevedPack {

        @Nonnull
        public static final StickerType REVED_DERP = new StickerType("Reved Derp", 17);
        @Nonnull
        public static final StickerType REVED_HI = new StickerType("Reved Hi", 18);
        @Nonnull
        public static final StickerType REVED_LOVE = new StickerType("Reved Love", 19);
        @Nonnull
        public static final StickerType REVED_PATRICK = new StickerType("Reved Patrick", 20);
        @Nonnull
        public static final StickerType REVED_RIP = new StickerType("Reved Rip", 21);
    }

    public static final class ValentineDay2020 {

        @Nonnull
        public static final StickerType HEART = new StickerType("Heart", 22);
        @Nonnull
        public static final StickerType LOVE_CHOCOLATE = new StickerType("Love Chocolate", 23);
        @Nonnull
        public static final StickerType LOVE_GLASSES = new StickerType("Love Glasses", 24);
        @Nonnull
        public static final StickerType LOVE_TEDDY = new StickerType("Love Teddy", 25);
        @Nonnull
        public static final StickerType LOVE_RINGS = new StickerType("Love Rings", 26);
    }

    public static final class StaySafePack {

        @Nonnull
        public static final StickerType SOCIAL_DISTANCING = new StickerType("Social Distancing", 27);
        @Nonnull
        public static final StickerType WASH_YOUR_HANDS = new StickerType("Wash your hands", 28);
        @Nonnull
        public static final StickerType DONT_TOUCH_YOUR_FACE = new StickerType("Don't touch your face", 29);
        @Nonnull
        public static final StickerType STAY_AT_HOME = new StickerType("Stay at home", 30);
        @Nonnull
        public static final StickerType VIRUS = new StickerType("Virus", 31);
    }

    public static final class FiveYearsLabyMod {

        @Nonnull
        public static final StickerType LABYMOD_LOGO = new StickerType("LabyMod Logo", 34);
        @Nonnull
        public static final StickerType LABYCOOL = new StickerType("LabyCool", 35);
        @Nonnull
        public static final StickerType FIVE_YEARS = new StickerType("5 Years", 36);
        @Nonnull
        public static final StickerType LABYWINGS = new StickerType("LabyWings", 37);
        @Nonnull
        public static final StickerType LABY_CHEST = new StickerType("Laby Chest", 38);
    }

    public static final class SpacePack {

        @Nonnull
        public static final StickerType PIKA_SPACE = new StickerType("Pika Space", 39);
        @Nonnull
        public static final StickerType POG_SPACE = new StickerType("Pog Space", 40);
        @Nonnull
        public static final StickerType SPACE_HI = new StickerType("Space Hi", 41);
        @Nonnull
        public static final StickerType SPACE_KRONE = new StickerType("Space Krone", 42);
        @Nonnull
        public static final StickerType SPACE_LOVE = new StickerType("Space Love", 43);
    }

    public static final class SchmockyyyPack {

        @Nonnull
        public static final StickerType SCHMOCKYYY_SUPERMAN = new StickerType("Schmockyyy Superman", 44);
        @Nonnull
        public static final StickerType SCHMOCKYYY_LOGO = new StickerType("Schmockyyy Logo", 45);
        @Nonnull
        public static final StickerType SCHMOCKYYY_GG = new StickerType("Schmockyyy GG", 46);
        @Nonnull
        public static final StickerType SCHMOCKYYY_YYY = new StickerType("Schmockyyy YYY", 47);
        @Nonnull
        public static final StickerType SCHMOCKYYY_BANANE = new StickerType("Schmockyyy Banane", 48);
    }

    public static final class FlexRacePack {

        @Nonnull
        public static final StickerType FLEXICAP = new StickerType("FlexiCap", 49);
        @Nonnull
        public static final StickerType FLEXICUTE = new StickerType("FlexiCute", 50);
        @Nonnull
        public static final StickerType FLEXILOST = new StickerType("FlexiLost", 51);
        @Nonnull
        public static final StickerType FLEXIUFF = new StickerType("FlexiUff", 52);
        @Nonnull
        public static final StickerType FLEXIWOW = new StickerType("FlexiWow", 53);
    }

    public static final class LumpiPack {

        @Nonnull
        public static final StickerType LUMPI_LOVE = new StickerType("Lumpi Love", 54);
        @Nonnull
        public static final StickerType LUMPI_ANGRY = new StickerType("Lumpi Angry", 55);
        @Nonnull
        public static final StickerType LUMPI_CLAP = new StickerType("Lumpi Clap", 56);
        @Nonnull
        public static final StickerType LUMPI_FACEPALM = new StickerType("Lumpi Facepalm", 57);
        @Nonnull
        public static final StickerType LUMPI_SAD = new StickerType("Lumpi Sad", 58);
    }

    public static final class SpiritendoPack {

        @Nonnull
        public static final StickerType SPIRITENDO_PLS = new StickerType("Spiritendo Pls", 59);
        @Nonnull
        public static final StickerType SPIRITENDO_SMUG = new StickerType("Spiritendo Smug", 60);
        @Nonnull
        public static final StickerType SPIRITENDO_PAT = new StickerType("Spiritendo Pat", 61);
        @Nonnull
        public static final StickerType SPIRITENDO_LOVE = new StickerType("Spiritendo Love", 62);
        @Nonnull
        public static final StickerType SPIRITENDO_SIP = new StickerType("Spiritendo Sip", 63);
    }

    public static final class Derkev99Pack {

        @Nonnull
        public static final StickerType DERKEV99_BAR = new StickerType("Derkev99 Bar", 64);
        @Nonnull
        public static final StickerType DERKEV99_BAR_HYPE = new StickerType("Derkev99 Bar Hype", 65);
        @Nonnull
        public static final StickerType DERKEV99_DAB = new StickerType("Derkev99 Dab", 66);
        @Nonnull
        public static final StickerType DERKEV99_WILD = new StickerType("Derkev99 Wild", 67);
        @Nonnull
        public static final StickerType DERKEV99_ZACK = new StickerType("Derkev99 Zack", 68);
    }

    public static final class FloexPack {

        @Nonnull
        public static final StickerType FLOEX_GG = new StickerType("Floex GG", 69);
        @Nonnull
        public static final StickerType FLOEX_KING = new StickerType("Floex King", 70);
        @Nonnull
        public static final StickerType FLOEX_LOVE = new StickerType("Floex Love", 71);
        @Nonnull
        public static final StickerType FLOEX_LAUGH = new StickerType("Floex Laugh", 72);
        @Nonnull
        public static final StickerType FLOEX_POPCORN = new StickerType("Floex Popcorn", 73);
    }

    public static final class FlagsPack {

        @Nonnull
        public static final StickerType GERMANY = new StickerType("Germany", 74);
        @Nonnull
        public static final StickerType UNITED_STATES = new StickerType("United States", 75);
        @Nonnull
        public static final StickerType FRANCE = new StickerType("France", 76);
        @Nonnull
        public static final StickerType SPAIN = new StickerType("Spain", 77);
        @Nonnull
        public static final StickerType ITALY = new StickerType("Italy", 78);
    }

    public static final class KroonulPack {

        @Nonnull
        public static final StickerType KROONUL_HEY = new StickerType("Kroonul Hey", 79);
        @Nonnull
        public static final StickerType KROONUL_LOST = new StickerType("Kroonul Lost", 80);
        @Nonnull
        public static final StickerType KROONUL_HYPE = new StickerType("Kroonul Hype", 81);
        @Nonnull
        public static final StickerType KROONUL_LAUGH = new StickerType("Kroonul Laugh", 82);
        @Nonnull
        public static final StickerType KROONUL_LOVE = new StickerType("Kroonul Love", 83);
    }

    public static final class DobermannxPack {

        @Nonnull
        public static final StickerType DOBERMANNX_KOPF = new StickerType("Dobermannx Kopf", 84);
        @Nonnull
        public static final StickerType DOBERMANNX_HOELLE = new StickerType("Dobermannx Hölle", 85);
        @Nonnull
        public static final StickerType DOBERMANNX_CITYBUILD_7 = new StickerType("Dobermannx Citybuild 7", 86);
        @Nonnull
        public static final StickerType DOBERMANNX_LOGO = new StickerType("Dobermannx Logo", 87);
        @Nonnull
        public static final StickerType DOBERMANNX_ALPHA = new StickerType("Dobermannx Alpha", 88);
    }

    public static final class xpiepsPack {

        @Nonnull
        public static final StickerType XPIEPS__BASIC = new StickerType("xpieps_ basic", 89);
        @Nonnull
        public static final StickerType XPIEPS__COMFY = new StickerType("xpieps_ comfy", 90);
        @Nonnull
        public static final StickerType XPIEPS__GIFT = new StickerType("xpieps_ gift", 91);
        @Nonnull
        public static final StickerType XPIEPS__COOKIE = new StickerType("xpieps_ cookie", 92);
        @Nonnull
        public static final StickerType XPIEPS__FLOWER = new StickerType("xpieps_ flower", 93);
    }

    public static final class PlayFlexPack {

        @Nonnull
        public static final StickerType PLAYFLEXFLEX = new StickerType("PLAYFLEXFLEX", 94);
        @Nonnull
        public static final StickerType PLAYFLEXHERZ = new StickerType("PLAYFLEXHERZ", 95);
        @Nonnull
        public static final StickerType PLAYFLEXMOIMEISTER = new StickerType("PLAYFLEXMOIMEISTER", 96);
        @Nonnull
        public static final StickerType PLAYFLEXVERSTECKEN = new StickerType("PLAYFLEXVERSTECKEN", 97);
        @Nonnull
        public static final StickerType PLAYFLEXLOOK = new StickerType("PLAYFLEXLOOK", 98);
    }

    public static final class SnokeLPPack {

        @Nonnull
        public static final StickerType SNOKELP_HYPE = new StickerType("SnokeLp HYPE", 99);
        @Nonnull
        public static final StickerType SNOKELP_HI = new StickerType("SnokeLP HI", 100);
        @Nonnull
        public static final StickerType SNOKELP_LOST = new StickerType("SnokeLP LOST", 101);
        @Nonnull
        public static final StickerType SNOKELP_LOVE = new StickerType("SnokeLP LOVE", 102);
        @Nonnull
        public static final StickerType SNOKELP_ANGRY = new StickerType("SnokeLP ANGRY", 103);
    }

    public static final class ValentineDay2021 {

        @Nonnull
        public static final StickerType GIVE_HEART = new StickerType("Give heart", 104);
        @Nonnull
        public static final StickerType HEART_ARROW = new StickerType("Heart arrow", 105);
        @Nonnull
        public static final StickerType HEART_EYES = new StickerType("Heart eyes", 106);
        @Nonnull
        public static final StickerType HEART_LOCKED = new StickerType("Heart locked", 107);
        @Nonnull
        public static final StickerType HEART_OPEN = new StickerType("Heart open", 108);
    }

    public static final class CooodexPack {

        @Nonnull
        public static final StickerType CODEX_SAD = new StickerType("Codex Sad", 109);
        @Nonnull
        public static final StickerType CODEX_ANGRY = new StickerType("Codex Angry", 110);
        @Nonnull
        public static final StickerType CODEX_HAPPY = new StickerType("Codex Happy", 111);
        @Nonnull
        public static final StickerType CODEX_GG = new StickerType("Codex GG", 112);
        @Nonnull
        public static final StickerType CODEX_LOVE = new StickerType("Codex Love", 113);
    }

    public static final class Issei_KunPack {

        @Nonnull
        public static final StickerType ISSEI_KUN_BORSTI = new StickerType("Issei_Kun Borsti", 114);
        @Nonnull
        public static final StickerType ISSEI_KUN_HASI = new StickerType("Issei_Kun Hasi", 115);
        @Nonnull
        public static final StickerType ISSEI_KUN_COOL = new StickerType("Issei_Kun Cool", 116);
        @Nonnull
        public static final StickerType ISSEI_KUN_EVIYOU = new StickerType("Issei_Kun Eviyou", 117);
        @Nonnull
        public static final StickerType ISSEI_KUN_ISSEI = new StickerType("Issei_Kun Issei", 118);
    }

    public static final class SkunkedPack {

        @Nonnull
        public static final StickerType SKUNKED_HEY_LOL = new StickerType("Skunked hey lol", 119);
        @Nonnull
        public static final StickerType SKUNKED_BUSSIN = new StickerType("Skunked BUSSIN", 120);
        @Nonnull
        public static final StickerType SKUNKED = new StickerType("Skunked !?", 121);
        @Nonnull
        public static final StickerType SKUNKED_LIP_BITE = new StickerType("Skunked lip bite", 122);
        @Nonnull
        public static final StickerType SKUNKED_WEARY = new StickerType("Skunked weary", 123);
    }

    public static final class OmarmuPack {

        @Nonnull
        public static final StickerType OMARMU_OWA = new StickerType("Omarmu Owa", 124);
        @Nonnull
        public static final StickerType OMARMU_HEART = new StickerType("Omarmu Heart", 125);
        @Nonnull
        public static final StickerType OMARMU_POG = new StickerType("Omarmu Pog", 126);
        @Nonnull
        public static final StickerType OMARMU_AWW = new StickerType("Omarmu Aww", 127);
        @Nonnull
        public static final StickerType OMARMU_ALLY = new StickerType("Omarmu Ally", 128);
    }

    public static final class LogischPack {

        @Nonnull
        public static final StickerType LOGISCH_WATER = new StickerType("Logisch Water", 129);
        @Nonnull
        public static final StickerType LOGISCH_RAGE = new StickerType("Logisch Rage", 130);
        @Nonnull
        public static final StickerType LOGISCH_HEY = new StickerType("Logisch Hey", 131);
        @Nonnull
        public static final StickerType LOGISCH_LOVE = new StickerType("Logisch Love", 132);
        @Nonnull
        public static final StickerType LOGISCH_ESEL = new StickerType("Logisch Esel", 133);
    }

    public static final class RubiPack {

        @Nonnull
        public static final StickerType RUBI_CURIOSO = new StickerType("Rubi Curioso", 134);
        @Nonnull
        public static final StickerType RUBI_FELIZ = new StickerType("Rubi Feliz", 135);
        @Nonnull
        public static final StickerType RUBI_MALARDO = new StickerType("Rubi Malardo", 136);
        @Nonnull
        public static final StickerType RUBI_QUE = new StickerType("Rubi Que", 137);
        @Nonnull
        public static final StickerType RUBI_SUSPIRO = new StickerType("Rubi Suspiro", 138);
    }

    public static final class iNubPack {

        @Nonnull
        public static final StickerType INUB_CFWG = new StickerType("iNub CFWG", 139);
        @Nonnull
        public static final StickerType INUB_HEART = new StickerType("iNub Heart", 140);
        @Nonnull
        public static final StickerType INUB_OJOS = new StickerType("iNub Ojos", 141);
        @Nonnull
        public static final StickerType INUB_MONKIES = new StickerType("iNub MonkieS", 142);
        @Nonnull
        public static final StickerType INUB_WEARY = new StickerType("iNub Weary", 143);
    }
}
