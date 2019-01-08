package asiantech.internship.summer.unittest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;
import asiantech.internship.summer.utils.Validate;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

public class TestPassword {
    @Spy
    private User mUser;

    @Before
    public void initMockito() {
        mUser = Mockito.spy(new User("faithBianQC09", ""));
    }

    @Test
    public void passwordRepeatCharacters() {
        doReturn("faithBianQC09").when(mUser).getPassword();
        assertEquals(Validate.validateLogin(mUser), R.string.errorPasswordRepeatChar);
    }

    @Test
    public void passwordNotHas2SpecialCharOrDigits() {
        doReturn("faithbian").when(mUser).getPassword();
        assertEquals(Validate.validateLogin(mUser), R.string.errorPasswordNotHas2SpecialCharOrDigits);
    }

    @Test
    public void passwordSmallestLength8Char() {
        doReturn("33@uhio").when(mUser).getPassword();
        assertEquals(Validate.validateLogin(mUser), R.string.errorPasswordSmallestLengthIs8Char);
    }

    @Test
    public void passwordNoMore2CharConsecutive() {
        doReturn("3@reeuertyuio").when(mUser).getPassword();
        assertEquals(Validate.validateLogin(mUser), R.string.errorPasswordNoMore2CharConsecutive);
    }

    @Test
    public void passwordNotEndWithDigitOrSpecialChar() {
        doReturn("3@reuertyuio13ew2!").when(mUser).getPassword();
        assertEquals(Validate.validateLogin(mUser), R.string.errorPasswordNotEndWithDigitOrSpecialChar);
    }

    @Test
    public void passwordAtLeast3UppercaseNonConsecutive() {
        doReturn("342reuesfWiEo").when(mUser).getPassword();
        assertEquals(Validate.validateLogin(mUser), R.string.errorPasswordAtLeast3UppercaseNonConsecutive);
    }
}
