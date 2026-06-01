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

/**
 * Constants used throughout the application.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface ApplicationConstants {
    
    public static final String APP_NAME =  Utils.getMavenModel().getName();
    public static final String APP_VERSION = Utils.getMavenModel().getVersion();
    public static final boolean IN_DEVELOPMENT = Boolean.parseBoolean( 
            Utils.getMavenModel().getProperties().getProperty( "development" ) );
    
    public static final String LIGHT_THEME = "LIGHT";
    public static final String DARK_THEME = "DARK";
    
    public static final int TURING_MACHINE_MAX_COUNT = 4000;

    // maximum recursion depth when building the PDA id tree. it is a backstop
    // for empty (epsilon) transition cycles that keep growing the stack (and so
    // never repeat the exact same configuration). it must stay well below the
    // depth that overflows the JVM stack, since buildIDTree is recursive
    public static final int PUSHDOWN_AUTOMATON_MAX_LEVEL = 1000;
    
}
