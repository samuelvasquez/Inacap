package samuelvasquez.inacap.cl.unidad3;


import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class Configuracion {
    public static void changeLocale(Activity activity, String language) {
        final Resources res = activity.getResources();
        final Configuration conf = res.getConfiguration();
        if (language == null || language.length() == 0) {
            conf.locale = Locale.getDefault();
        } else {
            final int idx = language.indexOf('-');
            if (idx != -1) {
                final String[] split = language.split("-");
                conf.locale = new Locale(split[0], split[1].substring(1));
            } else {
                conf.locale = new Locale(language);
            }
        }

        res.updateConfiguration(conf, null);
    }
}
