package asiantech.internship.summer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import asiantech.internship.summer.model.Account;
import asiantech.internship.summer.unittest.UtilValidate;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountValidationTest {
    @Mock
    private Account mAccountUsername;
    @Spy
    private Account mAccountPassword;

    @Test
    public void testCaseLengthUser() {
        when(mAccountUsername.getUsername()).thenReturn("");
        assertFalse(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        assertTrue(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("trandinhphuabcdefghij");
        assertFalse(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
    }

    @Test
    public void testCaseContainUpcase() {
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER_UPCASE);
        assertEquals(UtilValidate.containUpcase(mAccountUsername.getUsername()), 3);
    }

    @Test
    public void testCaseUpcaseContinous() {
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        assertFalse(UtilValidate.isUpcaseNotContinous(mAccountUsername.getUsername(), 2));
        when(mAccountUsername.getUsername()).thenReturn("TranDinhPhuAA");
        assertFalse(UtilValidate.isUpcaseNotContinous(mAccountUsername.getUsername(), 2));
        when(mAccountUsername.getUsername()).thenReturn("TranDinhPhuA");
        assertTrue(UtilValidate.isUpcaseNotContinous(mAccountUsername.getUsername(), 2));
    }

    @Test
    public void testCaseUsernameNotSpecialCharacter() {
        when(mAccountUsername.getUsername()).thenReturn(" trandinhphu");
        assertFalse(UtilValidate.isUsernameNotSpecialCharacter(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("trandinhph#");
        assertFalse(UtilValidate.isUsernameNotSpecialCharacter(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        assertTrue(UtilValidate.isUsernameNotSpecialCharacter(mAccountUsername.getUsername()));
    }

    @Test
    public void testCaseUsernameIsContainDigit() {
        when(mAccountUsername.getUsername()).thenReturn("Tran0DinhPhu");
        assertEquals(UtilValidate.sumNumberOfUsername(mAccountUsername.getUsername()), 1);
    }

    @Test
    public void testCaseUsernameHaveNotMoreTwoDigitContinous() {
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        assertTrue(UtilValidate.isUsernameHaveNotMoreTwoDigitContinous(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("trandinhphu123");
        assertFalse(UtilValidate.isUsernameHaveNotMoreTwoDigitContinous(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("trandinhphu12");
        assertTrue(UtilValidate.isUsernameHaveNotMoreTwoDigitContinous(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("1phutrandinh12");
        assertTrue(UtilValidate.isUsernameHaveNotMoreTwoDigitContinous(mAccountUsername.getUsername()));
    }

    @Test
    public void testCaseUsernameDoNotStartUpperCase() {
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        assertTrue(UtilValidate.isUsernameStartLowerCase(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER_UPCASE);
        assertFalse(UtilValidate.isUsernameStartLowerCase(mAccountUsername.getUsername()));
    }

    @Test
    public void testCasePasswordIsNotUser() {
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        doReturn("dinhphu").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordNotUser(mAccountUsername.getUsername(), mAccountPassword.getPassword()));
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        doReturn("Trandinhphu").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordNotUser(mAccountUsername.getUsername(), mAccountPassword.getPassword()));
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        doReturn(UtilValidate.USER).when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordNotUser(mAccountUsername.getUsername(), mAccountPassword.getPassword()));
    }

    @Test
    public void testCasePasswordContainsDigitAndSpecial() {
        doReturn(UtilValidate.USER).when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordContainsDigitAndSpecial(mAccountPassword.getPassword()));
        doReturn("tran  dinhphu").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordContainsDigitAndSpecial(mAccountPassword.getPassword()));
    }

    @Test
    public void testCaseCheckLengthPassword() {
        doReturn("phutran").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isLengthPasswordRight(mAccountPassword.getPassword()));
        doReturn("phutran123").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isLengthPasswordRight(mAccountPassword.getPassword()));
    }

    @Test
    public void testCasePasswordNotRepeatCharacter() {
        doReturn("trandinhphuu").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordNotRepeatCharacter(mAccountPassword.getPassword()));
        doReturn(UtilValidate.USER).when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordNotRepeatCharacter(mAccountPassword.getPassword()));
    }

    @Test
    public void testCasePasswordDoNotEndWithCharacterOrDigit() {
        doReturn("phutran123").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordDoNotEndWithDigitOrCharacter(mAccountPassword.getPassword()));
        doReturn("phutran123#").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordDoNotEndWithDigitOrCharacter(mAccountPassword.getPassword()));
        doReturn(UtilValidate.PASS).when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordDoNotEndWithDigitOrCharacter(mAccountPassword.getPassword()));
    }

    @Test
    public void testCaseUpcaseNotMoreTwoUpperCaseContinous() {
        doReturn("TranDinhphu").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isUpcaseNotMoreTwoUpperCaseContinous(mAccountPassword.getPassword(), 3));
        doReturn("trandinhphuUUU").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isUpcaseNotMoreTwoUpperCaseContinous(mAccountPassword.getPassword(), 3));
        doReturn(UtilValidate.USER_UPCASE).when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isUpcaseNotMoreTwoUpperCaseContinous(mAccountPassword.getPassword(), 3));
    }

    @Test
    public void testCaseResultLogin() {
        when(mAccountUsername.getUsername()).thenReturn("phu");
        doReturn(UtilValidate.PASS).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.CHECK_LENGTH_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER);
        doReturn(UtilValidate.PASS).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.UPPER_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn("tran DinhPhu");
        doReturn(UtilValidate.PASS).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.USERNAME_IS_NOT_SPECIAL);
        when(mAccountUsername.getUsername()).thenReturn("tranDinhPhu234");
        doReturn(UtilValidate.PASS).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.USERNAME_CONTAIN_DIGIT);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USER_UPCASE);
        doReturn(UtilValidate.PASS).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.START_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn(UtilValidate.USERNAME_RIGHT).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.PASSWORD_IS_NOT_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn(UtilValidate.USER).when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.PASSWORD_CONTAIN_SPECIAL);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn("tran").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.CHECK_LENGTH_PASSWORD);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn("tran1dinh22phu").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.CHARACRER_PASSWORD_REPEAT);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn("tran1dinhphu1").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.END_PASSWORD);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn("tran1dinhphu1a").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.UPPER_PASSWORD);
        when(mAccountUsername.getUsername()).thenReturn(UtilValidate.USERNAME_RIGHT);
        doReturn("tran1DinhPhu1A").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.SUCCESSFUL);
    }
}
