// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;

// trying something, may not be correct implementation

final class MyJSONParser implements JSONParser {

	private char[] buffer;
	private int bufferOffset;
	private int index;
	
	private char ch;
	private StringBuilder captureBuffer;

	  
	  
  @Override
	public JSON parse(String in) throws IOException {
		// TODO: implement this
	  
		if (in == null)
			throw new IOException("String in is null");
		buffer = new char[in.length()];
		for(int i=0; i<in.length(); i++) {
			buffer[i] = in.charAt(i);
		}
		this.index = 0;
		
		//return new MyJSON();
		
		return readJSONObject(in);
	}
  
    private JSON readJSONObject(String in) throws IOException {
    	JSON json;
		skipSpace();
		if(in.charAt(index) != '{') throw new IOException("Not a Valid JSON");
		index++;
		json = readObject();
		skipSpace();
		System.out.println(in.length() + " index: " + index);
		if(index < in.length()) throw new IOException("Extra stuff after JSON object");
		return json;
    }
    
    private JSON readObject() throws IOException {
    	String key="";
    	String value="";
    	MyJSON json = new MyJSON();
    	skipSpace();
    	char ch = readChar(); index++;
    	if(ch == '}') {  // reached end of object;
    		return json;
    	}
    	
    	do {
    	switch(ch) {
    	case '"':
    			key = readString();
    			System.out.println(key);
    			skipSpace();
    			ch = readChar();
    			index++;
    		break;
    	case ':':
    			skipSpace();
    			if(readChar() =='"') {
    				index++;
    				value = readString();
    				System.out.println(value);
    				json.setString(key,  value);
    				skipSpace();
    				ch = readChar();
    				index++;
    			} else	if(readChar() =='{') {
    				index++;
    				JSON j = readObject();
    				json.setObject(key, j);
    				skipSpace();
    				ch = readChar();
    				index++;
    			}
    			break;
    	case '\n':
    	case '\r':
    	case '\t':
    		index++;
    		ch=readChar();
    	case ',':
    		   skipSpace();
   				ch = readChar();
   				index++;
    		   break;
    	}
    	
    	} while ( (ch != '}'));
    	
    	
    	return json;
    }

	private char escapeStuff() throws IOException {

		char ch = readChar();

		switch (ch) {
		case '\\':
			return ch;

		case '"':
			return ch;
		case 'n':
			return ('\n');

		case 't':
			return ('\t');

		default:
			throw new IOException("Invalid JSON");
		}

	}

    private char readChar() throws IOException{
    	if(index >= buffer.length) throw new IOException("Invalid JSON");
    	return buffer[index];
    }
    
	private String readString() throws IOException{
		String ret = "";
		captureBuffer = new StringBuilder();
		while (readChar() != '"') {
			switch (readChar()) {
			case '\\':
				index++;
				captureBuffer.append(escapeStuff());
				index++;
				break;
			default:
				captureBuffer.append(readChar());
				index++;
			}
		}
		ret = captureBuffer.toString();
		index++;
		return ret;
	}
  
    private void skipSpace() throws IOException{
    	if(index == buffer.length) return;
    	while(isSpaceChar(readChar())) {
    		index++;
    	}
    }
  
    private boolean isSpaceChar(char ch) {
    	return ch == ' ' || ch == '\t' || ch == '\n' || ch =='\r';
    }
}

