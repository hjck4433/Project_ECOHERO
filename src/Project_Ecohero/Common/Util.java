package Project_Ecohero.Common;

public class Util {
    public boolean checkInputOnlyNumberAndAlphabet(String userId) {

        char chrInput;

        for (int i = 0; i < userId.length(); i++) {

            chrInput = userId.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크

            if (chrInput >= 0x61 && chrInput <= 0x7A) {
                // 영문(소문자) OK!
            }
            else if (chrInput >=0x41 && chrInput <= 0x5A) {
                // 영문(대문자) OK!
            }
            else if (chrInput >= 0x30 && chrInput <= 0x39) {

                // 숫자 OK!
            }
            else {
                return false;   // 영문자도 아니고 숫자도 아님!
            }

        }
        return true;
    }
}
