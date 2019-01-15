package asiantech.internship.summer.unittest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class UsernameTest {
    @Mock
    private User mUser;

    @Before
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isUsernameEmpty() {
        when(mUser.getUsername()).thenReturn("");
        assertFalse(ValidateUtils.isUsernameEmpty(mUser));
    }

    @Test
    public void testUsernameLengthLessThan8Letters() {
        when(mUser.getUsername()).thenReturn("");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameLengthMoreThan8AndLessThan21);
    }

    @Test
    public void testUsernameLengthMoreThan21Letters() {
        when(mUser.getUsername()).thenReturn("lequocbao@asiantech.vn");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameLengthMoreThan8AndLessThan21);
    }

    @Test
    public void testUsernameNotAtLeast2NonConsecutiveUppercaseLetters() {
        when(mUser.getUsername()).thenReturn("lequocBao");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsenameAtLeast2NonConsecutiveUppercaseLetters);
    }

    @Test
    public void testUsernameContainSpace() {
        when(mUser.getUsername()).thenReturn("leQuoc Bao");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameNotContainSpace);
    }

    @Test
    public void testUsernameContainsSpecialLetter() {
        when(mUser.getUsername()).thenReturn("leQuocBao!@#");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameNotContainSpecialCharacters);
    }

    @Test
    public void testUsernameNotAtMost2ConsecutiveDigits() {
        when(mUser.getUsername()).thenReturn("leQuocBao123");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameAtMost2ConsecutiveDigits);
    }

    @Test
    public void testUsernameStartWithUppercaseLetter() {
        when(mUser.getUsername()).thenReturn("LeQuocBaoPro79");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameNotStartWithUppercaseLettersOrDigits);
    }

    @Test
    public void testUsernameStartWithDigital() {
        when(mUser.getUsername()).thenReturn("1BaoDepTrai69");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.errorUsernameNotStartWithUppercaseLettersOrDigits);
    }

    @Test
    public void testUsernameValid() {
        when(mUser.getUsername()).thenReturn("baoDepTrai99");
        assertEquals(ValidateUtils.validateUserName(mUser.getUsername()), R.string.validUsername);
    }
}
