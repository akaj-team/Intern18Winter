package asiantech.internship.summer.unittest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;
import asiantech.internship.summer.unittest.utils.Validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doReturn;

public class PasswordTest {
    @Spy
    private User mUser;

    @Before
    public void init() {
        mUser = Mockito.spy(new User("BaoDepTrai", ""));
    }

    @Test
    public void passwordNotEmpty() {
        doReturn("a").when(mUser).getPassword();
        assertFalse(Validate.isEmptyPassword(mUser));
    }

    @Test
    public void passwordNotAtLeast2SpecialCharactersOrDigits() {
        doReturn("BaoLe").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.errorPasswordAtLeast2SpecialCharactersOrDigits);
    }

    @Test
    public void passwordLengthLessThan8() {
        doReturn("Bao123").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.errorPasswordLengthAtLeast8);
    }

    @Test
    public void passwordNotAtMost2ConsecutiveRepeatedLetters() {
        doReturn("LeQuocBao9999").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.errorPasswordAtMost2ConsecutiveRepeatedLetters);
    }

    @Test
    public void passwordEndWithDigit() {
        doReturn("LeQuocBao#1").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.errorPasswordNotEndWithDigitsOrSpecialLetters);
    }

    @Test
    public void passwordEndWithSpecialCharacter() {
        doReturn("LeQuocBao1#").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.errorPasswordNotEndWithDigitsOrSpecialLetters);
    }

    @Test
    public void passwordNotAtLeast3NonConsecutiveUppercaseLetters() {
        doReturn("quocbao#1deptrai").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.errorPasswordAtLeast3NonConsecutiveUppercaseLetters);
    }

    @Test
    public void passwordAndUsernameMatch() {
        doReturn("BaoDepTrai").when(mUser).getPassword();
        assertEquals(Validate.checkUserName(mUser), R.string.PasswordMatchesUsername);
    }

    @Test
    public void passwordAndUsernameNotMatch() {
        doReturn("BaoDepTrai123").when(mUser).getPassword();
        assertEquals(Validate.checkUserName(mUser), R.string.PasswordNotMatchUsername);
    }

    @Test
    public void passwordValid() {
        doReturn("QuocBao#1DepTrai").when(mUser).getPassword();
        assertEquals(Validate.validatePassword(mUser.getPassword()), R.string.validPassword);
    }
}
