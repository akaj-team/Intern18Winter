package asiantech.internship.summer.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import asiantech.internship.summer.models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Validation {
    @Mock
    private User mUserUsername;

    @Spy
    private User mUserPassword;

    @Before
    public void init() {
        mUserPassword = Mockito.spy(new User("aVandinhTruong", ""));
    }

    @Test
    public void testUsernameLength() {
        when(mUserUsername.getUsername()).thenReturn("toidosh");
        assertFalse(Validate.isUsernameLength(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("toidohHvg");
        Assert.assertTrue(Validate.isUsernameLength(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("van3d9");
        assertEquals(Validate.checkLogin(mUserUsername), Validate.USERNAME_9_20_CHARATER);
    }

    @Test
    public void testUsernameAtLeastTwoUpperCase() {
        when(mUserUsername.getUsername()).thenReturn("vandohvanDg");
        assertFalse(Validate.isUsernameAtLeastTwoNonConsecutiveUppercaseLetters(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("vandDohvanDg");
        Assert.assertTrue(Validate.isUsernameAtLeastTwoNonConsecutiveUppercaseLetters(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("vafdgnAA3d9");
        assertEquals(Validate.checkLogin(mUserUsername), Validate.USERNAME_AT_LEAST_2_UPPER_CASE);
    }

    @Test
    public void testUsernameShouldNotHaveSpecialCharacter() {
        when(mUserUsername.getUsername()).thenReturn("toid ohHvanDg");
        assertFalse(Validate.isUsernameSpecialCharacters(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("toidohHvanDg");
        Assert.assertTrue(Validate.isUsernameSpecialCharacters(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("van3d9.LhvanDg");
        assertEquals(Validate.checkLogin(mUserUsername), Validate.USERNAME_SHOULD_NOT_HAVE_SPECIAL_CHARACTER);
    }

    @Test
    public void testUsernameAtMostTwoNumber() {
        when(mUserUsername.getUsername()).thenReturn("van123dOhvanDg");
        assertFalse(Validate.isUsernameAtMostTwoConsecutiveNumericCharacters(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("van12dJhvanDg");
        Assert.assertTrue(Validate.isUsernameAtMostTwoConsecutiveNumericCharacters(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("van333d9DinhVt");
        assertEquals(Validate.checkLogin(mUserUsername), Validate.USERNAME_SHOULD_HAVE_AT_MOST_2_NUMBER);
    }

    @Test
    public void testUsernameStartUpperCase() {
        when(mUserUsername.getUsername()).thenReturn("12AnhDi12nh");
        assertFalse(Validate.isUsernameDoNotStartWithCapitalLettersOrNumbers(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("anhDi12nh");
        Assert.assertTrue(Validate.isUsernameDoNotStartWithCapitalLettersOrNumbers(mUserUsername.getUsername()));
        when(mUserUsername.getUsername()).thenReturn("Aan3d9LhvanDg");
        assertEquals(Validate.checkLogin(mUserUsername), Validate.USERNAME_SHOULD_NOT_START_WHITH_UPPER_CASE);
    }

    @Test
    public void testPasswordDifferentUsername() {
        doReturn("dinhvanyh").when(mUserPassword).getPassword();
        Assert.assertTrue(Validate.isPasswordDifferentUsername(mUserPassword));
        doReturn("aVandinhTruong").when(mUserPassword).getPassword();
        assertFalse(Validate.isPasswordDifferentUsername(mUserPassword));
        when(mUserPassword.getPassword()).thenReturn("aVandinhTruong");
        assertEquals(Validate.checkLogin(mUserPassword), Validate.USERNAME_AND_PASSWORD_ARE_DIFFERENT);
    }

    @Test
    public void passwordAtLeastTwoSpecialCharacterOrDigit() {
        doReturn("dinhvan12").when(mUserPassword).getPassword();
        Assert.assertTrue(String.valueOf(Validate.isPasswordAtLeastTwoSpecialCharactersOrNumbers(mUserPassword.getPassword())), true);
        doReturn("dinhvan1212").when(mUserPassword).getPassword();
        assertFalse(String.valueOf(Validate.isPasswordAtLeastTwoSpecialCharactersOrNumbers(mUserPassword.getPassword())), false);
        when(mUserPassword.getPassword()).thenReturn("aVandinhTruon");
        assertEquals(Validate.checkLogin(mUserPassword), Validate.PASSWORD_SHOULD_HAVE_2_SPECIAL_OR_NUMBER);
    }

    @Test
    public void passwordLoopCharTwoTimes() {
        doReturn("eeezxczxccc").when(mUserPassword).getPassword();
        Assert.assertTrue(String.valueOf(Validate.isPasswordLoopCharacterMoreThanTwoTimesInARow(mUserPassword.getPassword())), true);
        doReturn("dasdasdasd").when(mUserPassword).getPassword();
        assertFalse(String.valueOf(Validate.isPasswordLoopCharacterMoreThanTwoTimesInARow(mUserPassword.getPassword())), false);
        when(mUserPassword.getPassword()).thenReturn("aVan8VF+.diiinhr");
        assertEquals(Validate.checkLogin(mUserPassword), Validate.PASSWORD_SHOULD_NOT_LOOP_CHARACTER_TWO_TIMES);
    }

    @Test
    public void passwordEndWithDigitOrSpecial() {
        doReturn("sdsfdsfd").when(mUserPassword).getPassword();
        Assert.assertTrue(Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(mUserPassword.getPassword()));
        doReturn("sdsfdsfd12").when(mUserPassword).getPassword();
        assertFalse(Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(mUserPassword.getPassword()));
        when(mUserPassword.getPassword()).thenReturn("aVan+.dinhTr12");
        assertEquals(Validate.checkLogin(mUserPassword), Validate.PASSWORD_SHOULD_NOT_END_DIGIT_OR_SEPECIAL_CHAR);
    }

    @Test
    public void passwordHasAtLeastThreeUpperCase() {
        doReturn("sdsAfAdsAfd").when(mUserPassword).getPassword();
        Assert.assertTrue(Validate.isPasswordAtMostThreeConsecutiveNumericCharacters(mUserPassword.getPassword()));
        doReturn("sdsfdAAAsfd12").when(mUserPassword).getPassword();
        assertFalse(Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(mUserPassword.getPassword()));
        when(mUserPassword.getPassword()).thenReturn("aVan8DGd+.HinhTr");
        assertEquals(Validate.checkLogin(mUserPassword), Validate.PASSWORD_AT_LEAST_3_UPPERCASE_NOT_CONSECUTIVE);
    }
}
