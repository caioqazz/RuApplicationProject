package br.ufop.ruapplicationmvvm.validate;

import org.junit.Test;

import br.ufop.ruapplicationmvvm.util.Validate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ValidateUnitTest {

    @Test
    public void cpfIsCorrect() {
        assertTrue(Validate.isCPF("59185415278"));
        assertTrue(Validate.isCPF("54347773366"));
        assertTrue(Validate.isCPF("67101636365"));
        assertTrue(Validate.isCPF("44344540301"));
    }

    @Test
    public void cpfIsNotCorrect() {
        assertFalse(Validate.isCPF("12312342312"));
        assertFalse(Validate.isCPF("423"));
        assertFalse(Validate.isCPF("sdgdf123543"));
        assertFalse(Validate.isCPF("AS4$%¨&uyja"));
    }

    @Test
    public void passwordIsNotCorrect() {
        assertFalse(Validate.isPassword("123$%@12"));
        assertFalse(Validate.isPassword("1-23$+12"));
        assertFalse(Validate.isPassword(""));
        assertFalse(Validate.isPassword("()[};."));
    }

    @Test
    public void passwordIsCorrect() {
        assertTrue(Validate.isPassword("12345678"));
        assertTrue(Validate.isPassword("ABC12345"));
        assertTrue(Validate.isPassword("123az123"));
    }

    @Test
    public void emailIsNotCorrect() {
        assertFalse(Validate.isEmail("manager.gmail@com"));
        assertFalse(Validate.isEmail("com.gmail@manager"));
        assertFalse(Validate.isEmail("managergmail@com"));
        assertFalse(Validate.isEmail("managergmail.com"));
    }

    @Test
    public void emailIsCorrect() {
        assertTrue(Validate.isEmail("manager@gmail.com"));
        assertTrue(Validate.isEmail("manager@hotmail.com"));
        assertTrue(Validate.isEmail("manager@gmail.com.br"));
    }
}