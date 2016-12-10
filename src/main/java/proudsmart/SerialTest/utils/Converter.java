package proudsmart.SerialTest.utils;

import java.util.ArrayList;

/**
 * This class provides a collection of converting functions between different data types and formats
 */

public class Converter {

    /**
     * This function converts a byte array to an Integer array list
     * @param ba a byte array
     * @return an array list of Integer
     */
    public static ArrayList<Integer> ByteArrayToIntegerArrayList(byte[] ba)
    {
        ArrayList<Integer> intValues = new ArrayList<Integer>();

        for(int i = 0; i < ba.length; i ++)
        {
            int v = ba[i];
            intValues.add(new Integer(v));
        }
        return intValues;
    }

    /**
     * This fucntion converts an arraylist of Integer to a byte array
     * @param ia an arraylist of Integer
     * @return a byte array
     */
    public static byte[] IntegerArrayListToByteArray(ArrayList<Integer> ia)
    {
        byte[] ba = new byte[ia.size()];

        for(int i = 0; i < ia.size(); i ++)
        {
            ba[i] = ia.get(i).byteValue();
        }
        return ba;
    }

    /**
     * This function adds a space after every two characters in the given String to make it be understood easier
     * @param s a String in hex format
     * @return a new String by adding a space after every two characters in the input String
     */
    public static String HexStringToSeparatedHexString(String s)
    {
        int index = 0;
        StringBuilder rtnString = new StringBuilder();

        while(index < s.length())
        {
            rtnString.append(s.substring(index, index+2));
            index += 2;

            if(index != s.length())
                rtnString.append( " ");
        }
        return rtnString.toString();
    }


    /**
     * This function converts a subarray (of size 4 with the given beginning index) of a byte array to an int value
     * @param buff the byte array
     * @param index the beginning index of the subarray
     * @return the int value of the subarray of size 4
     */
    public static int ByteArrayToInt(byte [] buff, int index)
    {
        int rtValue = (int) buff[index];
        rtValue = (int) (rtValue << 24);
        rtValue &= 0xFF000000;

        int temp = (int) buff[index + 1];
        temp = (int) (temp << 16);
        temp &= 0x00FF0000;
        rtValue = (int) (rtValue | temp);

        temp = (int) buff[index + 2];
        temp = (int) (temp << 8);
        temp &= 0x0000FF00;
        rtValue = (int) (rtValue | temp);

        temp = (int) buff[index + 3];
        temp &= 0x000000FF;
        rtValue = (int) (rtValue | temp);
        return rtValue;
    }

    /**
     * This function converts a subarray (of size 4 with the given beginning index) of a byte array to a float value
     * @param buff the byte array
     * @param index the beginning index of the subarray
     * @return the float value of the subarray of size 4
     */
    public static float ByteArrayToFloat(byte [] buff, int index)
    {
        int temp = ByteArrayToInt(buff, index);
        return Float.intBitsToFloat(temp);
    }

    /**
     * This function converts a subarray (of size 2 with the given beginning index) of a byte array to a char value
     * @param buff the byte array
     * @param index the beginning index of the subarray
     * @return the char value of the subarray of size 2
     */
    public static char ByteArrayToChar(byte [] buff, int index)
    {
    	if(index >buff.length -2){
    		System.out.println("index:"+index);
    		System.out.println("buff length:"+buff.length);
    		return 0xff;
    	}
    	char rtValue = 0xff;
    	try{
	        rtValue = (char) buff[index];
	        rtValue = (char) (rtValue << 8);
	        rtValue &= 0xFF00;
	
	        char temp = (char) buff[index + 1];
	        temp &= 0x00FF;
	        rtValue = (char) (rtValue | temp);
    	}catch(ArrayIndexOutOfBoundsException e){
    		e.printStackTrace();
    		System.out.println("index:"+index);
    		System.out.println("buff length:"+buff.length);
    		rtValue = 0xff;
    	}
        return rtValue;
    }

    /**
     * This function converts an int value to a byte array of size 4
     * @param c the int value
     * @return a byte array of size 4
     */
    public static byte [] IntToByteArray(int c)
    {
        byte [] byteArray = new byte [4];
        byteArray [0] = (byte)((c >> 24) & 0x000000ff);
        byteArray [1] = (byte)((c >> 16) & 0x000000ff);
        byteArray [2] = (byte)((c >> 8) & 0x000000ff);
        byteArray [3] = (byte)(c & 0x000000ff);
        return byteArray;
    }

    /**
     * This function converts a long value to a char array of size 4
     * @param c the long value
     * @return a char array of size 4
     */
    public static char [] LongToCharArray(long c)
    {
        char[] charArray = new char[4];
        charArray [3] = (char)((c >> 48) & 0x000000000000ffff);
        charArray [2] = (char)((c >> 32) & 0x000000000000ffff);
        charArray [1] = (char)((c >> 16) & 0x000000000000ffff);
        charArray [0] = (char)(c & 0x000000000000ffff);
        return charArray;
    }

    /**
     * This function converts a char to a byte array of size 2
     * @param c a char value
     * @return a byte array of size 2
     */
    public static byte [] CharToByteArray(char c)
    {
        byte [] byteArray = new byte [2];
        byteArray [0] = (byte)((c >> 8) & 0x00ff);
        byteArray [1] = (byte)(c & 0x00ff);
        return byteArray;
    }

    /**
     * This function converts a String with hex format to an array of int value, every eight characters in the String is converted to one int value
     * @param s a String with hex format
     * @return an array of int value
     */
    public static int [] HexStringToInt(String s)
    {
        // the size of the byte array
        int length = s.length() / 8;
        int [] intArray = new int[length];

        int index = 0;
        for(int i = 0; i < length; i ++)
        {
            String subStr = s.substring(index, index + 8);
            //intArray [i] = Integer.parseInt(subStr, 16); //cannot handle input greater than 0x7FFF FFFF
            intArray [i] = (int)Long.parseLong(subStr, 16);
            index += 8;
        }
        return intArray;
    }

    /**
     * This function converts a String in hex format to an array of float value
     * @param s a String with hex format
     * @return an array of float value
     */
    public static float [] HexStringToFloat(String s)
    {
        int [] temp = HexStringToInt(s);
        float [] result = new float[temp.length];
        for(int i=0; i<temp.length; i++)
            result[i] = Float.intBitsToFloat(temp[i]);
        return result;
    }

    /**
     * This function converts a String with hex format to an array of char value, every four characters in the String is converted to one char value
     * @param s a String with hex format
     * @return an array of char values
     */
    public static char [] HexStringToChar(String s)
    {
        // the size of the byte array
        int length = s.length() / 4;
        char [] charArray = new char[length];

        int index = 0;
        for(int i = 0; i < length; i ++)
        {
            String subStr = s.substring(index, index + 4);
            charArray [i] = (char)(Integer.parseInt(subStr, 16) & 0x0000FFFF);
            index += 4;
        }
        return charArray;
    }

    /**
     * This function converts a String with hex format to an array of byte value, every two characters in the String is converted to one byte value
     * @param s a String with hex format
     * @return an array of byte values
     */
    public static byte [] HexStringToByte(String s)
    {
        // the size of the byte array
        int length = s.length() / 2;
        byte [] byteArray = new byte[length];

        int index = 0;
        for(int i = 0; i < length; i ++)
        {
            String subStr = s.substring(index, index + 2);
            byteArray [i] = (byte)(Integer.parseInt(subStr, 16) & 0x000000FF);
            index += 2;
        }
        return byteArray;
    }

    /**
     * This function converts an array of byte values to a String in hex format
     * @param ba an array of byte values
     * @return a String in hex format
     */
    public static String ByteArrayToHexString(byte [] ba)
    {
        StringBuilder  output = new StringBuilder();
        for(int i = 0; i < ba.length; i ++)
            output.append(ByteToHexString(ba[i]));
        return output.toString();
    }

    /**
     * This function converts an array of char values into a String with hex format
     * @param ca an array of char values
     * @return a String in hex format
     */
    public static String CharArrayToHexString(char [] ca)
    {
    	StringBuilder  output = new StringBuilder();
        for(int i = 0; i < ca.length; i ++)
            output .append(CharToHexString(ca[i]));
        return output.toString();
    }

    /**
     * This function converts an array of int values into a String with hex format
     * @param ia an array of int values
     * @return a String with hex format
     */
    public static String IntArrayToHexString(int [] ia)
    {
    	StringBuilder  output = new StringBuilder();
        for(int i = 0; i < ia.length; i ++)
            output.append(IntToHexString(ia[i]));
        return output.toString();
    }

    /**
     * This function converts an array of float values into a String with hex format
     * @param ia an array of float values
     * @return a String with hex format
     */
    public static String FloatArrayToHexString(float [] fa)
    {
        String output = "";
        for(int i = 0; i < fa.length; i ++)
            output += FloatToHexString(fa[i]);
        return output;
    }

    /**
     * This function converts an array of byte values into a String with hex format
     * @param ia an array of byte values
     * @return a String with hex format
     */

    public static String ByteToHexString(byte b)
    {
        if((b & 0x80) != 0)
        {
            char c = (char) b;
            c &= 0x00FF;
            return Integer.toHexString(c);
        }
        else
        {
            if((b & 0xF0) == 0)
                return "0" + Integer.toHexString(b);
            else
                return Integer.toHexString(b);
        }
    }

    /**
     * This function converts a char value to a String with hex format
     * @param c a char value
     * @return a String with hex format
     */
    public static String CharToHexString(char c)
    {
        String output = "";

        if((c & 0x8000) != 0)
        {
            int i = (int) c;
            i &= 0x0000FFFF;
            return Integer.toHexString(i);
        }
        else
        {
            if((c & 0xF000) != 0)
                return Integer.toHexString(c);
            else
            {
                output += "0";
                if((c & 0x0F00) != 0)
                    return output += Integer.toHexString(c);
                else
                {
                    output += "0";
                    if((c & 0x00F0) != 0)
                        return output += Integer.toHexString(c);
                    else
                    {
                        output += "0";
                        if((c & 0x000F) != 0)
                            return output += Integer.toHexString(c);
                        else
                        {
                            output += "0";
                            return output;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * This function converts an int value to a String with hex format
     * @param c an int value
     * @return a String with hex format
     */
    public static String IntToHexString(int c)
    {
        String output = "";
        char h = (char) ((c >> 16) & 0x0000FFFF);
        char l = (char) (c & 0x0000FFFF);

        output = CharToHexString(h) + CharToHexString(l);
        return output;
    }

    /**
     * This function converts a float value to a String with hex format
     * @param c a float value
     * @return a String with hex format
     */
    public static String FloatToHexString(float f)
    {
        int temp = Float.floatToIntBits(f);
        return IntToHexString(temp);
    }
    
    /**
     * This function converts a normal string to a string with hex format
     * @param s the original string
     * @return the hex formatted string
     */
    public static String StringToHexString(String s)
    {
        String temp = s;
        StringBuffer sb = new StringBuffer("");
        
        for(int i = 0;i<temp.length();i++)
            sb.append(CharToHexString(temp.charAt(i)));
        temp = sb.toString();
        return temp;
    }
    
    public static String HexStringToString(String s)
    {
        String temp;
        char[] c = HexStringToChar(s);
        temp = new String(c);
        return temp;
    }
    
    public static byte[] StringToByte(String s)
    {
        String temp_str = StringToHexString(s);
        byte[] temp_byte = HexStringToByte(temp_str);
        return temp_byte;
    }
    
    public static String ByteToString(byte[]ba, int start, int length)
    {
        if(ba.length<start + length)
            return "";
        
        StringBuffer hexStr = new StringBuffer("");
        
        for(int i = 0;i<length;i++)
            hexStr.append(ByteToHexString(ba[start + i]));
        
        String temp = HexStringToString(hexStr.toString());
        return temp;
    }
    
    /**
     *  @param hex_str the hex string to calculate the length
     *  @return the length of the hex str in char format
     */
    public static char getCmdLength(String hex_str)
    {
        char length = (char)(HexStringToByte(hex_str)).length;
        return length;
    }
    
    
	public static byte[] extractBytesFromTextArea(String strOriginal) {
		String str = "";

		if (strOriginal.equals("")) // for payload, return empty array
		{
			return new byte[0];
		}

		if (strOriginal.length() >= 2) // remove "0x" if user typed it
		{
			if ((strOriginal.substring(0, 2)).equals("0x")) {
				strOriginal = strOriginal.substring(2);
			}
		}

		// remove any special characters from input, such as '\n'
		for (int i = 0; i < strOriginal.length(); i++) {
			char temp = strOriginal.charAt(i);
			if (Character.isLetter(temp) || Character.isDigit(temp)) {
				str = str + temp; // add this character to input string
			}
		}
		// System.out.println(str);
		// convert hex string to bytes
		byte[] tempData = null;
		try {
			tempData = Converter.HexStringToByte(str); // will truncate
														// incomplete(odd number
														// of) bytes (must have
														// 2 hex chars per byte)
		} catch (Exception e) {
			return null; // do not sent any data to gateway.
		}
		// warn user if they input odd number of hex chars
		if (str.length() % 2 != 0) {
			// sentTextArea.append("Warning: Uneven hex string, truncating last
			// hex charactater\n");
		}

		int length = tempData.length; // may be any number of bytes
		if (length > 72) // restrict # of bytes to 72 or less
		{
			length = 72;
			// sentTextArea.append("Warning: Truncating input to 72 bytes\n");
		}

		// return up to 72 bytes parsed from text area.
		byte[] buff = new byte[length];
		System.arraycopy(tempData, 0, buff, 0, length); // keep only the first
														// 72 bytes in data
		return buff;
	}
	
	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}
}



