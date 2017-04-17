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

import java.util.Collection;
import java.util.HashSet;

final class TestMain {

  public static void main(String[] args) {

    final Tester tests = new Tester();

   tests.add("Empty Object", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ }");

        final Collection<String> strings = new HashSet<>();
        obj.getStrings(strings);

        Asserts.isEqual(strings.size(), 0);

        final Collection<String> objects = new HashSet<>();
        obj.getObjects(objects);

        Asserts.isEqual(objects.size(), 0);
      }
    });
    
    tests.add("String Value", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {
          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name\":\"sam doe\"}");

         // Asserts.isEqual("sam doe", obj.getString("name"));
          Asserts.isEqual("sam doe", obj.getString("name"));
       }
      });
    
    
    tests.add("String Value with all escapes", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {
          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"nam\\\\\t\ne\":\"sam doe\"}");

         // Asserts.isEqual("sam doe", obj.getString("name"));
          Asserts.isEqual("sam doe", obj.getString("nam\\\t\ne"));
       }
      });
    
    
    tests.add("String many Values with white spaces", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{         \"name\":\"sam doe\" , \"name2\":\"sam doe2\" , \"name2\":\"sam doe2\" , \"name2\":\"sam doe2\"              }");

       // Asserts.isEqual("sam doe", obj.getString("name"));
        Asserts.isEqual("sam doe2", obj.getString("name2"));
     }
    });
    
    tests.add("String Values with no white spaces", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {
          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{\"name\":\"sam doe\",\"name2\":\"sam doe2\"}");

         Asserts.isEqual("sam doe", obj.getString("name"));
         // Asserts.isEqual("sam doe2", obj.getString("name2"));
       }
      });
    
    tests.add("String Values with extra } at end", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {
          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{\"name\":\"sam doe\",\"name2\":\"sam doe2\"}   }");

         Asserts.isEqual("sam doe", obj.getString("name"));
         // Asserts.isEqual("sam doe2", obj.getString("name2"));
       }
      });
    

    tests.add("Object Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));
      }
    });

    tests.add("Object Value & String value", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" ,\"myfirst\":\"rithu\",\"mylast\":\"simha\"} }");

          final JSON nameObj = obj.getObject("name");

          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam", nameObj.getString("first"));
          Asserts.isEqual("doe", nameObj.getString("last"));
          Asserts.isEqual("rithu", nameObj.getString("myfirst"));
          Asserts.isEqual("simha", nameObj.getString("mylast"));
        }
      });
    
    tests.add("Two Object Values", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\"} , \"name2\":{\"myfirst\":\"tom\", \"mylast\":\"smith\"} }");

          final JSON nameObj = obj.getObject("name");
          final JSON name2Obj = obj.getObject("name2");
          
          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam", nameObj.getString("first"));
          Asserts.isEqual("doe", nameObj.getString("last"));
          Asserts.isEqual("tom", name2Obj.getString("myfirst"));
          Asserts.isEqual("smith", name2Obj.getString("mylast"));
        }
      });
    
    tests.run(new JSONFactory(){
      @Override
      public JSONParser parser() {
        return new MyJSONParser();
      }

      @Override
      public JSON object() {
        return new MyJSON();
      }
    });
  }
}

