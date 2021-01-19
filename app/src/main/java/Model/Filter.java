package Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.deizaci.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Filter {

    public Boolean getbKafic() {
        return bKafic;
    }

    public void setbKafic(Boolean bKafic) {
        this.bKafic = bKafic;
    }

    public Boolean getbPab() {
        return bPab;
    }

    public void setbPab(Boolean bPab) {
        this.bPab = bPab;
    }

    public Boolean getbKlub() {
        return bKlub;
    }

    public void setbKlub(Boolean bKlub) {
        this.bKlub = bKlub;
    }

    private Boolean bKafic = true;
    private Boolean bPab = true;
    private Boolean bKlub = true;

    public Boolean getbSvirke() {
        return bSvirke;
    }

    public void setbSvirke(Boolean bSvirke) {
        this.bSvirke = bSvirke;
    }

    private Boolean bSvirke = false;

    public Boolean getbSplav() {
        return bSplav;
    }

    public void setbSplav(Boolean bSplav) {
        this.bSplav = bSplav;
    }

    public Boolean getbKafana() {
        return bKafana;
    }

    public void setbKafana(Boolean bKafana) {
        this.bKafana = bKafana;
    }

    private Boolean bSplav = true;
    private Boolean bKafana = true;
    public Filter(View view,Activity activity){

        ImageView filter = view.findViewById(R.id.filter);


        filter.setOnClickListener(v -> {
            @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchKafic,switchPab,switchKlub,switchKafana,switchSplav,switchSvirke;

            BottomSheetDialog filterSheet = new BottomSheetDialog(view.getContext(),R.style.SheetDialog);
            filterSheet.setContentView(R.layout.bottom_filter);
            filterSheet.setCanceledOnTouchOutside(true);


            switchKafic = filterSheet.findViewById(R.id.kaficF);
            switchPab = filterSheet.findViewById(R.id.pabF);
            switchKlub = filterSheet.findViewById(R.id.klubF);
            switchKafana = filterSheet.findViewById(R.id.kafanaF);
            switchSplav = filterSheet.findViewById(R.id.splavF);
            switchSvirke = filterSheet.findViewById(R.id.svirke);
            assert switchPab != null;
            switchPab.setChecked(bPab);
            assert switchKafic != null;
            switchKafic.setChecked(bKafic);
            assert switchKlub != null;
            switchKlub.setChecked(bKlub);

            assert switchSplav != null;
            switchSplav.setChecked(bSplav);
            assert switchKafana != null;
            switchKafana.setChecked(bKafana);
            assert switchSvirke != null;
            switchSvirke.setChecked(bSvirke);

            switchPab.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) activity);
            switchKafic.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) activity);
            switchKlub.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) activity);
            switchSplav.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) activity);
            switchKafana.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) activity);
            switchSvirke.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) activity);

            filterSheet.show();

        });

    }
}
