package com.example.dan.telegraphkeyboard;

import java.util.StringTokenizer;

/**
 * Created by Michael on 4/2/2016.
 */
public class Translator
{
    // Take a single character in Morse Code and translate it to alphanumeric
    public static String translateChar(String morseString)
    {
        String text;

        switch (morseString)
        {
            // Letters
            case ".-":
                text = "A";
                break;
            case "-...":
                text = "B";
                break;
            case "-.-.":
                text = "C";
                break;
            case "-..":
                text = "D";
                break;
            case ".":
                text = "E";
                break;
            case "..-.":
                text = "F";
                break;
            case "--.":
                text = "G";
                break;
            case "....":
                text = "H";
                break;
            case "..":
                text = "I";
                break;
            case ".---":
                text = "J";
                break;
            case "-.-":
                text = "K";
                break;
            case ".-..":
                text = "L";
                break;
            case "--":
                text = "M";
                break;
            case "-.":
                text = "N";
                break;
            case "---":
                text = "O";
                break;
            case ".--.":
                text = "P";
                break;
            case "--.-":
                text = "Q";
                break;
            case ".-.":
                text = "R";
                break;
            case "...":
                text = "S";
                break;
            case "-":
                text = "T";
                break;
            case "..-":
                text = "U";
                break;
            case "...-":
                text = "V";
                break;
            case ".--":
                text = "W";
                break;
            case "-..-":
                text = "X";
                break;
            case "-.--":
                text = "Y";
                break;
            case "--..":
                text = "Z";
                break;

            // Digits
            case ".----":
                text = "1";
                break;
            case "..---":
                text = "2";
                break;
            case "...--":
                text = "3";
                break;
            case "....-":
                text = "4";
                break;
            case ".....":
                text = "5";
                break;
            case "-....":
                text = "6";
                break;
            case "--...":
                text = "7";
                break;
            case "---..":
                text = "8";
                break;
            case "----.":
                text = "9";
                break;
            case "-----":
                text = "0";
                break;

            // Punctuation etc.
            case ".-.-.-":
                text = ".";
                break;
            case "--..--":
                text = ",";
                break;
            case "---...":
                text = ":";
                break;
            case "-....-":
                text = "-";
                break;
            case ".----.":
                text = "'";
                break;
            case "-..-.":
                text = "/";
                break;
            case "..--..":
                text = "?";
                break;
            case "-.--.-":
                text = ")"; // Open and close parens are the same symbol, but we can only have one here
                break;

            // SOS is actually its own symbol
            case "...---...":
                text = "SOS";
                break;

            // Ignore bad input
            default:
                text = "";
                break;
        }

        return text;
    }


    // Takes a Morse string, returns its English translation
    public static String translate(String morse)
    {
        String alpha = ""; // the alphanumeric translation of the Morse code
        StringTokenizer strTok = new StringTokenizer(morse);

        while(strTok.hasMoreElements())
        {
            String tok = strTok.nextToken();
            if (tok.equals("/"))
            {
                alpha += " ";
            }
            else
            {
                alpha += translateChar(tok);
            }
        }

        return alpha;
    }
}
