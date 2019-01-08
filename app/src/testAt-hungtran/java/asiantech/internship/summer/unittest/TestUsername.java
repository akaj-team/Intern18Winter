package asiantech.internship.summer.unittest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;
import asiantech.internship.summer.utils.Validate;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestUsername {
    @Mock
    private User mUser;

    @Before
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void usernameLength() {
        when(mUser.getUserName()).thenReturn("dsaddd");
        assertEquals(Validate.ValidateLogin(mUser), R.string.errorUsernameLength);
    }

    @Test
    public void usernameNotAtLeast2UppercasenonConsecutive() {
        when(mUser.getUserName()).thenReturn("dsaenjkDs");
        assertEquals(Validate.ValidateLogin(mUser), R.string.errorUsernameLeastTwoNonConsecutiveUppercaseLetters);
    }

    @Test
    public void usernameHasSpaceAndSpecialCharacters() {
        when(mUser.getUserName()).thenReturn("dsaenjkDsD.");
        assertEquals(Validate.ValidateLogin(mUser), R.string.errorUserNameNoSpecialCharactersAndSpace);
    }

    @Test
    public void usernameMoreThan2DigitsConsecutive() {
        when(mUser.getUserName()).thenReturn("dsaenjkDsD321");
        assertEquals(Validate.ValidateLogin(mUser), R.string.errorUserNameNoAtMost2ConsecivitiveDigits);
    }

    @Test
    public void usernameStartWithUppercaseAndDigit() {
        when(mUser.getUserName()).thenReturn("DdsaenjkDsD32");
        assertEquals(Validate.ValidateLogin(mUser), R.string.errorUserNameNotBeginWithUppercaseAndDigit);
    }
}
