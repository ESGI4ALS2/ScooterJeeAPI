package com.scooterjee.utils;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.email.EmailAddress;

public class Utils {
    public static User defaultValidUser = new User(1L, "bob", "bob", "p",
                                                  "0601020304",
                                                  new EmailAddress("test@test.com"),
                                                  new Address(1L, "", "", "", "", "", 0.0, 0.0));
}
