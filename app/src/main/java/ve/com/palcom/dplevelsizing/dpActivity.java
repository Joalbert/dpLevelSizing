package ve.com.palcom.dplevelsizing;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdRegistration;


public class dpActivity extends AppCompatActivity implements View.OnClickListener
{
    Button submitting;
    AdLayout adLayout;
    SectionPageAdapter adapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_dp);
        Toolbar mtoolbar=(Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mtoolbar);

        submitting = (Button) findViewById(R.id.submit);
        //xxxxx : it should be changed by your amazon code for the banner
        amazonBanner("xxxxx");

        mViewPager=(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout mTabHost=(TabLayout) findViewById(R.id.tabs);
        mTabHost.setupWithViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager viewPager){
        adapter=new SectionPageAdapter(getSupportFragmentManager());
        String [] names=getResources().getStringArray(R.array.legs);
        adapter.addFragment(new dpFragment(),names[0]);
        adapter.addFragment(new ppsFragment(),names[1]);
        adapter.addFragment(new pFragment(),names[2]);
        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onStart(){
        super.onStart();
        submitting.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

    }

    public void amazonBanner(String key){
        AdRegistration.setAppKey(key);
        AdRegistration.enableTesting(true);
        AdRegistration.enableLogging(true);
        adLayout=(AdLayout) findViewById(R.id.ad_view);
        adLayout.loadAd();
        adLayout.showAd();
        adLayout.enableAutoShow();
        adLayout.setTimeout(60000);
    }

    @Override
    public void onClick(View v)
    {
        int [] editTextIds;
        int len;
        String [] val;
        int position=mViewPager.getCurrentItem();
        switch (position)
        {
            case 0:
                dpFragment mdpLevelCalculation=(dpFragment)
                        adapter.getItem(position);
                editTextIds=mdpLevelCalculation.getEditTextIds();
                len=editTextIds.length;
                val=new String[len];

                for(int i=0;i<len;i++){
                    val[i] = "0";
                }
                dpPresenter.setResult(mdpLevelCalculation.getOutTextView(),
                    mdpLevelCalculation.getOutSpinner(),
                        findViewById(R.id.container),
                        dpPresenter.getLevelCalcCase(
                            mdpLevelCalculation.getEditTextIds(),
                            val, mdpLevelCalculation.getInSpinner(),
                                findViewById(R.id.container)
                    )
            );
                break;
            case 1:
                ppsFragment mppsLevelCalculation=(ppsFragment)
                        adapter.getItem(position);
                editTextIds=mppsLevelCalculation.getEditTextIds();
                len=editTextIds.length;
                val=new String[len];

                for(int i=0;i<len;i++){
                    val[i] = "0";
                }

                dpPresenter.setResult(mppsLevelCalculation.getOutTextView(),
                    mppsLevelCalculation.getOutSpinner(), findViewById(R.id.container),
                    dpPresenter.getLevelCalcCase(
                            mppsLevelCalculation.getEditTextIds(),
                            val, mppsLevelCalculation.getInSpinner(),
                            findViewById(R.id.container)
                    )
            );
                break;
            case 2:
                pFragment mpLevelCalculation=(pFragment)
                        adapter.getItem(position);
                editTextIds=mpLevelCalculation.getEditTextIds();
                len=editTextIds.length;
                val=new String[len];

                for(int i=0;i<len;i++){
                    val[i] = "0";
                }

                dpPresenter.setResult(mpLevelCalculation.getOutTextView(),
                    mpLevelCalculation.getOutSpinner(), findViewById(R.id.container),
                    dpPresenter.getLevelCalcCase(
                            mpLevelCalculation.getEditTextIds(),
                            val, mpLevelCalculation.getInSpinner(),
                            findViewById(R.id.container)
                    )
            );
                break;
        }

    }

    @Override
    protected void onDestroy(){
    super.onDestroy();
    this.adLayout.destroy();
    }


}
