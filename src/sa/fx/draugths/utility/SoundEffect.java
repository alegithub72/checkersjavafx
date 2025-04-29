package sa.fx.draugths.utility;

public enum SoundEffect {

    FIRE("fire5.wav", true),
    SPEEDY_BITE("speedy_bite.wav", true),
    BITE("Dogbite.wav", false),
    BIG_BITE("lion_roar_2.mp3", false),
    JUNGLE("jungle_drum.wav", false),
    MARCH("marcias.wav", false),
    MOVESPACESOLDIER("jerpack.wav", true),
    CLOPETE_DOUBLE("moveAlien2.wav", true),
    MOVEMONSTER("UFO.wav", false),
    EXPLOSIONMONSTER("Fireball.wav", false),
    MISSILE("top.wav", false),
    ELICOPTER("elicopter.wav", false),
    CLOPETE("move_alien.wav", true),
    EXPLOSION("Fireball.wav", false),
    EXPLOSION_LASER("explosion-39897.wav", false),
    BIGEXPLOSION("Explosion3.wav", false),
    ACHW("Achievement.wav", false),
    ACHB("pluck.wav", false),
    WING("159355-wing-effect.wav", true),
    JETPACK("jerpack.wav", true),
    JET("jet.wav", true),
    DRONE("mini-quadcopter-flying-loop-80330.wav",true),
    LASER("191594_laser.wav", false),
    LASER_MM("191594_laser.wav", true),
    MUSIC_SIGLA("muppet.mp3", false),
    MUSIC_CELEBRATION("270545_jingle-win-01.wav", false),
    EFFECT_HEY("416507_hey.wav", false),
    EFFECT_COIN("29649-coin-return.wav", false),
    SAPCESHIP_BUZZ("55829_electric.wav", false),
    SAPCESHIP("510018-buzzy.wav", true),
    POLICE_JUMP("policeJump.wav", false);
    private String file;
    private boolean loop;

    SoundEffect(String file, boolean loop) {

        this.file = file;
        this.loop = loop;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

}
