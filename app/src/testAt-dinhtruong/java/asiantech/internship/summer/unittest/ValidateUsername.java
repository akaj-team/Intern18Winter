package asiantech.internship.summer.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidateUsername {
    @Mock
    private User mUser;

    @Test
    public void testUsernameLength() {
        when(mUser.getUsername()).thenReturn("vandinh");
        assertEquals(Validate.checkLogin(mUser), R.string.usernameShouldHave920Characters);
        when(mUser.getUsername()).thenReturn("vandinhahihihihidfdsf");
        assertEquals(Validate.checkLogin(mUser), R.string.usernameShouldHave920Characters);
    }

    @Test
    public void testUsernameAtLeastTwoUpperCase() {
        when(mUser.getUsername()).thenReturn("vanDDinhvan");
        assertEquals(Validate.checkLogin(mUser), R.string.usernameShouldHaveAtLeast2NonConsecutiveUpperCaseLetters);
    }

    @Test
    public void testUsernameHaveNotSpecialCharacter() {
        when(mUser.getUsername()).thenReturn("dinh1234   ");
        assertEquals(Validate.checkLogin(mUser), R.string.theUsernameShouldNotHaveSpecialCharacters);
    }

    @Test
    public void testUsernameStartUpperCase() {
        when(mUser.getUsername()).thenReturn("AAsadsadas");
        assertEquals(Validate.checkLogin(mUser), R.string.usernameDoNotStartWithCapitalLettersOrNumbers);
    }
}
