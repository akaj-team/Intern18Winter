package asiantech.internship.summer.unittest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

public class ValidatePassword {
    @Spy
    private User mUser;

    @Before
    public void init() {
        mUser = Mockito.spy(new User("Dinh123", ""));
    }

    @Test
    public void passwordEqualsUsername() {
        doReturn("vAn123dDinh").when(mUser).getPassword();
        assertEquals(Validate.checkLogin(mUser), R.string.theUsernameAndPasswordMustBeDifferent);
    }

    @Test
    public void passwordRepeatChar() {
        doReturn("ndaewgeerieR").when(mUser).getPassword();
        assertEquals(Validate.checkLogin(mUser), R.string.theUsernameAndPasswordMustBeDifferent);
    }

    @Test
    public void passwordNotHas2SpecialChar() {
        doReturn("nbewgerier").when(mUser).getPassword();
        assertEquals(Validate.checkLogin(mUser), R.string.theUsernameAndPasswordMustBeDifferent);
    }
}
