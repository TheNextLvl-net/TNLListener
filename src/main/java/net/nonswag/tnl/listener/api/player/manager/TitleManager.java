package net.nonswag.tnl.listener.api.player.manager;

import net.kyori.adventure.text.Component;
import net.nonswag.core.api.object.Pair;
import net.nonswag.tnl.listener.api.packets.outgoing.SetActionBarTextPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.TitlePacket;
import net.nonswag.tnl.listener.api.title.Title;

import javax.annotation.Nullable;

public abstract class TitleManager extends Manager {

    @Nullable
    private Pair<Title.Animation, Thread> titleAnimation = null;

    public void resetTitle() {
        TitlePacket.ClearTitles.create(true).send(getPlayer());
    }

    public void sendTitle(Title title) {
        TitlePacket.SetTitlesAnimation.create(title.getTimeIn(), title.getTimeStay(), title.getTimeOut()).send(getPlayer());
        if (title.getTitle() != null) {
            TitlePacket.SetTitleText.create(Component.text(title.getTitle())).send(getPlayer());
        }
        if (title.getSubtitle() != null) {
            TitlePacket.SetSubtitleText.create(Component.text(title.getSubtitle())).send(getPlayer());
        }
    }

    @Nullable
    public Title.Animation getTitleAnimation() {
        return titleAnimation == null ? null : titleAnimation.getKey();
    }

    public boolean stopTitleAnimation() {
        if (titleAnimation == null || titleAnimation.getValue() == null) return false;
        titleAnimation.getValue().interrupt();
        return true;
    }

    public void sendTitle(Title.Animation animation) {
        sendTitle(animation, success -> {
        });
    }

    public void sendTitle(Title.Animation animation, Title.Animation.Finished finished) {
        if (animation.getTitle() == null) throw new NullPointerException("You have to define a title");
        stopTitleAnimation();
        Thread thread = new Thread(() -> {
            try {
                String[] split = animation.getTitle().split("");
                for (int i = 10; i >= 0; i--) {
                    sendTitle(new Title((animation.getDesign().secondaryColor() +
                            "- " +
                            animation.getDesign().primaryColor() +
                            String.join(" ".repeat(i), split) +
                            animation.getDesign().secondaryColor() +
                            " -"),
                            animation.getDesign().extraColor() + animation.getSubtitle(),
                            0, animation.getTimeStay(), animation.getTimeOut()));
                    Thread.sleep(50);
                }
                titleAnimation = null;
                finished.finished(true);
            } catch (InterruptedException ignored) {
                titleAnimation = null;
                finished.finished(false);
            }
        }, "Animation Thread");
        titleAnimation = new Pair<>(animation, thread);
        thread.start();
    }

    public void sendActionbar(Component actionbar) {
        SetActionBarTextPacket.create(actionbar).send(getPlayer());
    }

    public void sendActionbar(String actionbar) {
        sendActionbar(Component.text(actionbar));
    }

    public void resetActionbar() {
        sendActionbar(Component.empty());
    }
}
