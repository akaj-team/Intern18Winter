package asiantech.internship.summer.unittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import asiantech.internship.summer.models.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ValidationPassword {
    @Spy
    private User mUser;

    @Before
    public void init() {
        mUser = Mockito.spy(new User("dinhvan", ""));
    }

    @Test
    public void passwordEqualsUsername() {
        doReturn("dinhvan12").when(mUser).getPassword();
        assertTrue(Validate.isPasswordDifferentUsername(mUser));
        doReturn("dinhvan").when(mUser).getPassword();
        assertFalse(Validate.isPasswordDifferentUsername(mUser));
    }

    @Test
    public void passwordAtLeastTwoSpecialCharacterOrDigit() {
        doReturn("dinhvan12").when(mUser).getPassword();
        assertTrue(String.valueOf(Validate.isPasswordAtLeastTwoSpecialCharactersOrNumbers(mUser.getPassword())), true);
        doReturn("dinhvan12").when(mUser).getPassword();
        assertFalse(String.valueOf(Validate.isPasswordAtLeastTwoSpecialCharactersOrNumbers(mUser.getPassword())), false);
    }

    @Test
    public void passwordRepeatCharTwoTimes() {
        doReturn("eeezxczxccc").when(mUser).getPassword();
        assertTrue(String.valueOf(Validate.isPasswordLoopCharacterMoreThanTwoTimesInARow(mUser.getPassword())), true);
        doReturn("dasdasdasd").when(mUser).getPassword();
        assertFalse(String.valueOf(Validate.isPasswordLoopCharacterMoreThanTwoTimesInARow(mUser.getPassword())), false);
    }

    @Test
    public void passwordEndWithDigitOrSpecial() {
        doReturn("sdsfdsfd").when(mUser).getPassword();
        assertTrue(Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(mUser.getPassword()));
        doReturn("sdsfdsfd12").when(mUser).getPassword();
        assertFalse(Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(mUser.getPassword()));
    }

    @Test
    public void passwordHasThreeUpperCase() {
        doReturn("sdsAfAdsAfd").when(mUser).getPassword();
        assertTrue(Validate.isPasswordAtMostThreeConsecutiveNumericCharacters(mUser.getPassword()));
        doReturn("sdsfdAAAsfd12").when(mUser).getPassword();
        assertFalse(Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(mUser.getPassword()));
    }
}
