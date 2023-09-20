public class MessageGenerator {
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public MessageGenerator() {}

    public String generateMsg(String msg) {
        String genMsg = String.valueOf(alphabet.indexOf(msg.charAt(0)) + 1); // Start with the first char in msg, because this
        for (int i = 1; i < msg.length(); i++) {
            String letter = String.valueOf(alphabet.indexOf(msg.charAt(i)) + 1); // +1 to accommodate for a=01
            if (letter.length() < 2)
                letter = "0" + letter;
            genMsg += letter;
        }

        return genMsg;
    }

    public String decryptMsg(String msg) {
        // Check if first number is cut, ex. 1xxx should be 01xxx
        if (msg.length() % 2 != 0) {
            msg = "0" + msg;
        }

        String translation = "";
        while (msg.length() > 0) {
            int letter = Integer.parseInt(msg.substring(0, 2));
            translation += alphabet.charAt(letter - 1);
            msg = msg.substring(2); // remove translated numbers from message
        }

        return translation;
    }
}
