package Model;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.deizaci.R;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigacija extends Fragment {

    private final MeowBottomNavigation navigacija;
    private final Context context;

    public int getPage_id1() {
        return page_id1;
    }

    private int page_id1;

    public void addFragment(Fragment fragment,String tag) {
        this.fragments.add(fragment);
        tags.add(tag);

    }
    public void setFragmentOnPozition(Fragment fragment,int position,String tag){
        fragments.set(position,fragment);
        tags.set(position,tag);

    }

    private final List<Fragment> fragments = new ArrayList<>();



    private final List<String> tags = new ArrayList<>();

    public void setPage_id1(int page_id1) {
        this.page_id1 = page_id1;
    }

    public BottomNavigacija(int page_id, Context context){
        this.page_id1 = page_id;
        this.context = context;
        navigacija =  ((Activity) context).findViewById(R.id.bottom_nav);
        navigacija.add(new MeowBottomNavigation.Model(1,R.drawable.korisnik));
        navigacija.add(new MeowBottomNavigation.Model(2,R.drawable.mapa));
        navigacija.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_local_cafe_24));

        navigacija.setOnClickMenuListener(item -> {

            if (item.getId() == 1){
                setViewPager(fragments.get(item.getId()-1), page_id1 > item.getId(), tags.get(item.getId()-1));
                page_id1 = item.getId();




            }
            if (item.getId() == 2){
                setViewPager(fragments.get(item.getId()-1), page_id1 > item.getId(), tags.get(item.getId()-1));
                page_id1 = item.getId();

            }
            if (item.getId() == 3){
                setViewPager(fragments.get(item.getId()-1), page_id1 > item.getId(), tags.get(item.getId()-1));
                page_id1 = item.getId();
            }



        });
       navigacija.setOnShowListener(item -> {

       });

        navigacija.setOnReselectListener(item -> {
            // your codes
        });

        navigacija.show(page_id,false);

    }

    public void show(){
        navigacija.show(page_id1,false);

    }
    public void show(int id){
        navigacija.show(id,false);

    }
    public void setViewPager(Fragment fragment, boolean x,String tag){

        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        if (!x)
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
       else
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

        transaction.replace(R.id.placeholder1, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();


    }
}
