package asiantech.internship.summer.unittest;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class PasswordTest {
    @Spy
    private User mUser;

    @Before
    public void initMockito() {
        mUser = Mockito.spy(new User("BaoDepTrai", ""));
    }

    @Test
    public void isPasswordEmpty(){
        when(mUser.getPassword()).thenReturn("");
        assertFalse(TextUtils.isEmpty(mUser.getPassword()));
    }

    @Test
    public void testPasswordLengthLessThan8() {
        doReturn("").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.errorPasswordLengthAtLeast8);
    }

    @Test
    public void testPasswordNotAtLeast2SpecialCharactersOrDigits() {
        doReturn("BaoLeQuoc").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.errorPasswordAtLeast2SpecialCharactersOrDigits);
    }


    @Test
    public void testPasswordNotAtMost2ConsecutiveRepeatedLetters() {
        doReturn("LeQuocBao999").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.errorPasswordAtMost2ConsecutiveRepeatedLetters);
    }

    @Test
    public void testPasswordEndWithDigit() {
        doReturn("LeQuocBao#1").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.errorPasswordNotEndWithDigitsOrSpecialLetters);
    }

    @Test
    public void testPasswordEndWithSpecialCharacter() {
        doReturn("LeQuocBao1#").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.errorPasswordNotEndWithDigitsOrSpecialLetters);
    }

    @Test
    public void testPasswordNotAtLeast3NonConsecutiveUppercaseLetters() {
        doReturn("quocbao#1deptrai").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.errorPasswordAtLeast3NonConsecutiveUppercaseLetters);
    }

    @Test
    public void testPasswordMatchesUsername() {
        doReturn("BaoDepTrai").when(mUser).getPassword();
        assertEquals(ValidateUtils.checkPasswordMatchesUsername(mUser), R.string.PasswordMatchesUsername);
    }

    @Test
    public void testPasswordNotMatchUsername() {
        doReturn("BaoDepTrai123").when(mUser).getPassword();
        assertEquals(ValidateUtils.checkPasswordMatchesUsername(mUser), R.string.PasswordNotMatchUsername);
    }

    @Test
    public void testPasswordValid() {
        doReturn("QuocBao#1DepTrai").when(mUser).getPassword();
        assertEquals(ValidateUtils.validatePassword(mUser.getPassword()), R.string.validPassword);
    }
}
