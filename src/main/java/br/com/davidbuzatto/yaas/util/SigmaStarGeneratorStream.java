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
import java.util.Set;

/**
 * Creates a stream of strings combining all symbols of an alphabet. It
 * simulates the generation of a infinite set like Σ*. Its possible
 * to specify which starting length of strings will be produced first.
 * 
 * This class maintain a cache of the already generated strings for
 * performance purposes. About 15*10^6 (15 million) strings per second on
 * the developing machine.
 * 
 * The generation only moves forward, i.e., once next() is called, you can't
 * get the previous generated string.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SigmaStarGeneratorStream {
    
    private final List<Character> alphabet;
    private List<String> generatingCache;
    private List<String> currentCache;
    
    private int currentLength;
    private int currentAlphabetPosition;
    private int currentBasePosition;

    public SigmaStarGeneratorStream( Set<Character> alphabet )
            throws IllegalArgumentException {
        this( alphabet, 0 );
    }
    
    public SigmaStarGeneratorStream( Set<Character> alphabet, int startingLength )
            throws IllegalArgumentException {
        this( new ArrayList<>( alphabet ), startingLength );
    }
    
    public SigmaStarGeneratorStream( List<Character> alphabet, int startingLength )
            throws IllegalArgumentException {
        
        if ( alphabet.isEmpty() ) {
            throw new IllegalArgumentException(
                    "The alphabet must contain at least one symbol." );
        }
        
        this.alphabet = alphabet;
        this.generatingCache = new ArrayList<>();
        this.currentCache = new ArrayList<>();
        
        while ( currentLength < startingLength ) {
            next();
        }
        
        if ( startingLength > 0 ) {
            currentBasePosition = 0;
            currentAlphabetPosition = 0;
            if ( !generatingCache.isEmpty() ) {
                generatingCache.remove( generatingCache.size() - 1 );
            }
        }
        
    }
    
    public String next() {
        
        if ( currentLength == 0 ) {
            generatingCache.add( "" );
            currentCache = generatingCache;
            generatingCache = new ArrayList<>();
            currentLength++;
            return "";
        } else {
            
            if ( currentBasePosition >= currentCache.size() ) {
                
                currentBasePosition = 0;
                currentAlphabetPosition++;
                
                if ( currentAlphabetPosition >= alphabet.size() ) {
                    currentAlphabetPosition = 0;
                    currentCache = generatingCache;
                    generatingCache = new ArrayList<>();
                    currentLength++;
                }
                
            }
            
            String newString = alphabet.get( currentAlphabetPosition ) + 
                    currentCache.get( currentBasePosition );
            generatingCache.add( newString );
            currentBasePosition++;
            
            return newString;
            
        }
        
    }
    
    // test
    public static void main( String[] args ) {
        
        List<Character> alphabet = new ArrayList<>();
        
        /*alphabet.add( '0' );
        alphabet.add( '1' );*/
        
        alphabet.add( 'a' );
        alphabet.add( 'b' );
        alphabet.add( 'c' );
        
        //SigmaStarGeneratorStream ssgs = new SigmaStarGeneratorStream( alphabet );
        SigmaStarGeneratorStream ssgs = new SigmaStarGeneratorStream( alphabet, 2 );
        long t = System.currentTimeMillis();
        for ( int i = 0; i < 100; i++ ) {
            System.out.println( ssgs.next() );
        }
        /*for ( int i = 0; i < 15000000; i++ ) {
            sgs.next();
        }*/
        /*for ( int i = 0; i < 100000000; i++ ) {
            sgs.next();
        }
        for ( int i = 0; i < 100000000; i++ ) {
            sgs.next();
        }*/
        System.out.println( System.currentTimeMillis() - t );
        
    }
    
}
