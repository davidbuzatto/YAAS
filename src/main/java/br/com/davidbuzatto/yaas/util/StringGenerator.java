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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Generates strings based on an alphabet and in the desired length.
 * This classe maintain a cache of the already generated strings.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class StringGenerator {
    
    private Set<Character> alphabet;
    private Map<Integer, List<String>> cache;

    public StringGenerator( Set<Character> alphabet ) {
        this.alphabet = alphabet;
        this.cache = new TreeMap<>();
    }
    
    public List<String> generate( int length ) {
        return new ArrayList<>();
    }
    
}
