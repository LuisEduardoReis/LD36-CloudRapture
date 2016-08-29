package com.kr_eddie.ld36.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.kr_eddie.ld36.LD36Game;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(LD36Game.V_WIDTH*LD36Game.SCALE, LD36Game.V_HEIGHT*LD36Game.SCALE);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new LD36Game();
        }
}