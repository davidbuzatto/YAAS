/*
 * Copyright (C) 2023 Prof. Dr. David Buzatto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.util;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Application preferences manager.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ApplicationPreferences {
    
    private static final String PREFERENCES_PATH = "br.com.davidbuzatto.yaas";
    private static final Preferences PREFS = Preferences.userRoot().node( PREFERENCES_PATH );
    
    public static final String PREF_DEFAULT_FOLDER_PATH = "PREF_DEFAULT_FOLDER_PATH";
    public static final String PREF_THEME = "PREF_THEME";
    
    public static void preparePreferences( boolean reset ) {
        
        if ( reset ) {
            PREFS.remove( PREF_DEFAULT_FOLDER_PATH );
            PREFS.remove( PREF_THEME );
        }
        
        PREFS.put( PREF_DEFAULT_FOLDER_PATH, PREFS.get( PREF_DEFAULT_FOLDER_PATH, new File( "" ).getAbsolutePath() ) );
        PREFS.put( PREF_THEME, PREFS.get( PREF_THEME, ApplicationConstants.DARK_THEME ) );
        
    }
    
    public static String getPref( String key ) {
        return PREFS.get( key, "" );
    }
    
    public static void setPref( String key, String value ) {
        PREFS.put( key, value );
    }
    
}
