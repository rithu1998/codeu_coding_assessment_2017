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


import java.util.*;

final class MyJSON implements JSON {
  HashMap<String, String> hStrings = new HashMap<String, String>();
  HashMap<String, JSON> hJSONObjects = new HashMap<String, JSON>();
  
  @Override
  public JSON getObject(String name) {
	JSON s = hJSONObjects.get(name);
	if(s != null)
		return s;
	else
		return null;
  }

  @Override
  public JSON setObject(String name, JSON value) {
	hJSONObjects.put(name, value);
    return this;
  }

  @Override
  public String getString(String name) {
	String s = hStrings.get(name);
	if(s != null) 
		return s;
	else
        return null;
  }

  @Override
  public JSON setString(String name, String value) {
	hStrings.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
	  for (String key : hJSONObjects.keySet()) {
			names.add((hJSONObjects.get(key)).toString());
	  }
	  
  }

  @Override
  public void getStrings(Collection<String> names) {
		for (String key : hStrings.keySet()) {
			names.add(hStrings.get(key));
		}
  }
}
