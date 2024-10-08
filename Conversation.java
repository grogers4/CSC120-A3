import java.util.Scanner;  // Import the Scanner class
import java.lang.reflect.Array; // Import Array class
import java.util.Random; //Import Random class

/*class for the conversation, contains all substantive code */
class Conversation {

  /*array of strings containing the canned responses */
  static String[] canned_responses = {"wow", "interesting", "I see", "uh huh", "oh"};

  /**
  * returns a random number within a given range
  * @param int minimun (inclusive) for the begining of the range 
  * @param int maximum (exclusive) for the end of the desried range
  * @returns a random number within the given range
  */
  public static int getRandomNumber(int min, int max) {
    Random random = new Random();
    return random.ints(min, max)
    .findFirst()
    .getAsInt();
  }

  /**
   * method to obtain a random canned response
   * @returns a random string selected from canned_responses
   */
    public static String getResponse() {
      String convo_response = canned_responses[getRandomNumber(0, 5)];
      return convo_response;
    }

  /* mirror words separated by spaces to detect presence of keywords in user's input without splitting */
  static String[] keywords_user = {" I ", " me ", " am ", " you ", " my ", " your ", " I'm ", " you're ", " are "};
  /* mirror words and replacements for response from user's input */
  static String[] keywords_user_unspaced = {"I", "me", "am", "you", "my", "your", "I'm", "you're", "are"};
  static String[] keywords_replacement_unspaced = {"you", "you", "are", "I", "your", "my", "you're", " I'm ", " am "};

  public static void main(String[] arguments) {
    // You will start the conversation here.

    Scanner user_input_scanner = new Scanner(System.in);
    System.out.println("How many rounds of conversation would you like?");
    int rounds = user_input_scanner.nextInt(); 
    user_input_scanner.nextLine(); // clear out extra \n
    int transcript_number = (rounds * 2) + 1;

    String[] transcript = new String[transcript_number];

    
    String convo_start = "Hey there, what's on your mind?";
    System.out.println(convo_start);
    int transcript_place = 0;
    Array.set(transcript, transcript_place, convo_start);
    transcript_place += 1;

    int i = 0;
    while (i < rounds) {
      String convo_line  = user_input_scanner.nextLine();
        Array.set(transcript, transcript_place, convo_line);
        transcript_place += 1;
        String user_convo_spaced = " " + convo_line + " ";
        Boolean keyword = false;
        for (int x = 0; x < 9; x++) {
          if (user_convo_spaced.contains(keywords_user[x])) {
          keyword = true;
          }
        }
        if (keyword) {
          String [] convo_line_array = convo_line.split(" ");
          String convo_response = "";
          int array_length = convo_line_array.length;
          int p;
          for (p = 0; p < array_length; p ++) {
            int u = 0;
            for (u = 0; u < 9; u ++) {
              if (convo_line_array[p].equals(keywords_user_unspaced[u])) { 
                convo_line_array[p] = keywords_replacement_unspaced[u];
                u = 8;
              }
            }
          }
          int v;
          for (v = 0; v < (array_length - 1); v++) {
            convo_response = convo_response + convo_line_array[v] + " ";
          }
          convo_response = convo_response + convo_line_array[(array_length - 1)] + "?";
          
          Array.set(transcript, transcript_place, convo_response);
          transcript_place += 1;
          System.out.println(convo_response);
        } else {
          String convo_response = getResponse();
          Array.set(transcript, transcript_place, convo_response);
          transcript_place += 1;
          System.out.println(convo_response);
        }
      i += 1;
    }
    user_input_scanner.close();

    System.out.println("\nTranscript:");
    for (int n = 0; n < transcript_number; n++) {
      System.out.println(transcript[n]);
    }
  }
}
