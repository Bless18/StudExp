package com.bless.studexp.Utils;

import com.google.firebase.auth.FirebaseAuth;

public interface RequiredVariables {
    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    int picId=111;
    String image_type="image";
    int st_picId=222;
}
