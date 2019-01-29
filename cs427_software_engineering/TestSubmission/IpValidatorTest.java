package WhiteBox;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IpValidatorTest {

    @Test
    public void testNegativeIpV4(){
        IpValidator tool = new IpValidatorImpl();
        assertEquals("Neither",tool.validIPAddress("0"));
        assertEquals("Neither",tool.validIPAddress(".0.0.0"));
        assertEquals("Neither",tool.validIPAddress("1.a.0.0"));
        assertEquals("Neither",tool.validIPAddress(".1.a.0.0"));
        assertEquals("Neither",tool.validIPAddress("1.a.0.0."));
        assertEquals("Neither",tool.validIPAddress("...."));
        assertEquals("Neither",tool.validIPAddress("300.-1.250.222"));
        assertEquals("Neither",tool.validIPAddress("021.-1.250.222"));
        assertEquals("Neither",tool.validIPAddress("300.300.300.300"));
        assertEquals("Neither",tool.validIPAddress("-1.-2.-3.-4"));
        assertEquals("Neither",tool.validIPAddress("&#48;.-2.-3.-4"));
        assertEquals("Neither",tool.validIPAddress("8e-7.128.1.1"));
        assertEquals("Neither",tool.validIPAddress("-0.128.1.1"));
    }

    @Test
    public void testNegativeIpV6 () {
        IpValidator tool = new IpValidatorImpl();
        assertEquals("Neither",tool.validIPAddress("fe80:::383c:ad72:fd0:10b3%16"));
        assertEquals("Neither",tool.validIPAddress("()))::()***:ad72:fd0:10b3%16"));

        assertEquals("Neither",tool.validIPAddress("2001:0  DB8::85A3:0000:0000:8a2e:0370:7334"));
        assertEquals("Neither",tool.validIPAddress("2001:0@db8::85a3:0000:0000:8a2e:0370:7334"));
        assertEquals("Neither",tool.validIPAddress("2001::FFFF::85a3:0000:0000:8a2e:0370:7334"));
        assertEquals("Neither",tool.validIPAddress("!@#$:!@#$:!@#$:!@#$:!@#$:!@#$:!@#$:!@#$"));
        assertEquals("Neither",tool.validIPAddress("!@#$:!@#$:FFFF:ffff:!@#$:!@#$:!@#$:!@#$"));
        assertEquals("Neither",tool.validIPAddress(":!@#$:!@#$:FFFF:ffff:!@#$:!@#$:!@#$:!@#$"));
        assertEquals("Neither",tool.validIPAddress("!@#$:!@#$:FFFF:ffff:!@#$:!@#$:!@#$:!@#$:"));

        assertEquals("Neither",tool.validIPAddress("2001:1:FFFF:ffff:!@#$:!@#$:!@#$:!@#$"));
        assertEquals("Neither",tool.validIPAddress("2001:11231:FFFF:ffff:!@#$:!@#$:!@#$:!@#$"));

        assertEquals("Neither",tool.validIPAddress("2001:0db8:85a3:0000:0000:8a2e:0370:fffff"));
        assertEquals("Neither",tool.validIPAddress("2001:0db8:85a3:0000:0000:8a2e::7334"));
        assertEquals("Neither",tool.validIPAddress("zzzz:FFFF:zzzz:0000:0000:8a2e:0370:"));
        assertEquals("Neither",tool.validIPAddress("ZZZZ:FFFF:zzzz:0000:0000:8a2e:0370:"));
        assertEquals("Neither",tool.validIPAddress("a2bc:FFFF:zzzz:0000:0000:8a2e:0370:"));

        assertEquals("Neither",tool.validIPAddress("ga88:fAab:cc11:CCDD:ccdd:DD21:1b7d:7334"));
        assertEquals("Neither",tool.validIPAddress("'a88:fAab:cc11:CCDD:ccdd:DD21:1b7d:7334"));
        assertEquals("Neither",tool.validIPAddress("GGGG:ffff:cc11:CCDD:ccdd:DD21:1b7d:7334"));
    }

    @Test
    public void testValidPositiveIpAddress () {
        IpValidator tool = new IpValidatorImpl();
        assertEquals("IPv4",tool.validIPAddress("123.0.0.1"));
        assertEquals("IPv6",tool.validIPAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
        assertEquals("IPv6",tool.validIPAddress("Fa88:fAab:cc11:CCDD:ccdd:DD21:1b7d:7334"));
        assertEquals("IPv6",tool.validIPAddress("ffff:fAab:cc11:CCDD:ccdd:DD21:1b7d:7334"));
        assertEquals("IPv6",tool.validIPAddress("aaaa:fAab:cc11:CCDD:ccdd:DD21:1b7d:7334"));
        assertEquals("IPv6",tool.validIPAddress("ba88:fAab:cc11:CCDD:ccdd:DD21:1b7d:7334"));
    }
}
