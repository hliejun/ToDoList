//@@author A0130620B
package todolist.parser;

import java.util.ArrayList;

import todolist.model.TokenizedCommand;

public class NormalCommandParser {

    public NormalCommandParser() {

    }

    public TokenizedCommand parse(String input) {

        String temp[] = input.split(" ");
        String action = temp[0];

        ArrayList<String> myList = new ArrayList<String>();

        String name = null;
        Boolean generateName = false;
        for (int i = 0; i < temp.length - 1; i++) {

            if (generateName == true) {
                if (temp[i + 1].contains("\"")) {
                    name = name + " " + temp[i + 1].replace("\"", "");
                    generateName = false;
                    myList.add(name);
                    // args[counter] = name;
                    // counter++;
                    name = null;

                } else {
                    name = name + " " + temp[i + 1];
                }
            } else {
                if (temp[i + 1].contains("\"")) {
                    name = temp[i + 1].replace("\"", "");
                    generateName = true;
                    int count = temp[i + 1].length() - temp[i + 1].replace("\"", "").length();
                    if (count == 2) {
                        generateName = false;
                        myList.add(name);
                        // args[counter] = name;
                        // counter++;
                        name = null;
                    }
                } else {
                    myList.add(temp[i + 1]);
                    // args[counter] = temp[i+1];
                    // counter++;
                }
            }
        }

        String[] args = myList.toArray(new String[0]);

        /*
         * String temp0[] = input.split("\" "); String temp1[] = temp0[1].split(
         * " \"");
         * 
         * String first[] = temp0[0].split(" "); String third[] =
         * temp1[1].split(" ");
         * 
         * String second = temp1[0];
         * 
         * int length = first.length + third.length + 1;
         * 
         * 
         * String temp[] = new String[length];
         * 
         * for(int i=0; i< length;i++) { if(i < first.length) { temp[i] =
         * first[i]; } else { if(i > first.length) { temp[i] = third[i -
         * first.length - 1]; } else { temp[i] = second; } } }
         * 
         * String action = temp[0]; String args[] = new String[temp.length - 1];
         * for(int i=0; i<temp.length-1; i++) { args[i] = temp[i + 1]; }
         */

        return new TokenizedCommand(convert(action), args);
    }
    
    
    private String convert(String input) {
    	if(input.equals("add") || input.equals("schedule")||input.equals("create")) {
    		return "add";
    	}
    	if(input.equals("delete") || input.equals("cancel")||input.equals("remove")) {
    		return "delete";
    	}
    	if(input.equals("edit") || input.equals("modify")||input.equals("change")||input.equals("replace")) {
    		return "edit";
    	}
    	if(input.equals("archive") || input.equals("done")||input.equals("complete") || input.equals("shelf") || input.equals("finish")) {
    		return "done";
    	}
    	if(input.equals("unarchive") || input.equals("undone")||input.equals("incomplete") || input.equals("unshelf") || input.equals("unfinish")) {
    		return "undone";
    	}
    	if(input.equals("postpone") || input.equals("delay")) {
    		return "postpone";
    	}
    	if(input.equals("forward") || input.equals("advance")) {
    		return "forward";
    	}
    	if(input.equals("label") || input.equals("categorize")||input.equals("tag")) {
    		return "label";
    	}
    	if(input.equals("open") || input.equals("load")) {
    		return "open";
    	}
    	return input;
    }
}
