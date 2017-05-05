package ve.com.palcom.dplevelsizing;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdRegistration;


public class dpActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener
        {
            Button submitting;
            //dpPsFragment mdppsLevelCalculation;
            dpFragment mdpLevelCalculation;
            ppsFragment mppsLevelCalculation;
            pFragment mpLevelCalculation;



            Spinner legTypes;
            private AdLayout adLayout;
            private int currentVariable;
            private TabHost mTabHost;

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_dp);
        submitting = (Button) findViewById(R.id.submit);

        /*
        mTabHost=(TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();

        TabHost.TabSpec mTabSpec=mTabHost.newTabSpec("DP Calc");
        mTabSpec.setContent(R.id.dppsContainer);
        mTabSpec.setIndicator("DP Calc");
        mTabHost.addTab(mTabSpec);

        mTabSpec=mTabHost.newTabSpec("LT one Leg");
        mTabSpec.setContent(R.id.pContainer);
        mTabSpec.setIndicator("LT one Leg");
        mTabHost.addTab(mTabSpec);

        mTabSpec=mTabHost.newTabSpec("LT one Leg - Pressurized");
        mTabSpec.setContent(R.id.pContainer);
        mTabSpec.setIndicator("LT one Leg - Pressurized");
        mTabHost.addTab(mTabSpec);
        */
                // Spinner Info Loading
                legTypes=(Spinner) findViewById(R.id.numberOfLegs);
                ArrayAdapter<CharSequence> numberLegs=ArrayAdapter.createFromResource(this,
                        R.array.legs,android.R.layout.simple_spinner_item);
                numberLegs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                legTypes.setAdapter(numberLegs);


                amazonBanner("99d0bfb1899b46bb8d9acd16a9f488af");

                if(savedInstanceState==null) {
                    currentVariable=0;
                    hideDpFragment();
                    hidePPsFragment();
                    hidePFragment();
                   // hideDpPsFragment();

                }
                else{
                    currentVariable=savedInstanceState.getInt("currentVariable");
                    mdpLevelCalculation=(dpFragment) getSupportFragmentManager().
                            getFragment(savedInstanceState, "mdpLevelCalculation");
                    mpLevelCalculation=(pFragment) getSupportFragmentManager().
                            getFragment(savedInstanceState, "mpLevelCalculation");
                    mppsLevelCalculation=(ppsFragment) getSupportFragmentManager().
                            getFragment(savedInstanceState, "mppsLevelCalculation");

                }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_d, menu);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
        submitting.setOnClickListener(this);
        legTypes.setOnItemSelectedListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("currentVariable",currentVariable);
        getSupportFragmentManager().putFragment(savedInstanceState,"mpLevelCalculation",
                mpLevelCalculation);
        getSupportFragmentManager().putFragment(savedInstanceState,"mppsLevelCalculation",
                mppsLevelCalculation);
        getSupportFragmentManager().putFragment(savedInstanceState,"mdpLevelCalculation",
                mdpLevelCalculation);

    }

            public void amazonBanner(String key){
                AdRegistration.setAppKey(key);
                AdRegistration.enableTesting(false);
                AdRegistration.enableLogging(false);
                adLayout=(AdLayout) findViewById(R.id.ad_view);
                adLayout.loadAd();
                adLayout.showAd();
                adLayout.enableAutoShow();
                adLayout.setTimeout(60000);
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


            @Override
            public void onClick(View v) {
                int [] editTextIds;
                int len;
                String [] val;

                switch (currentVariable) {
                    case 0:
                        editTextIds=mdpLevelCalculation.getEditTextIds();
                        len=editTextIds.length;
                        val=new String[len];

                        for(int i=0;i<len;i++){
                            val[i] = "0";
                        }

                        dpPresenter.setResult(mdpLevelCalculation.getOutTextView(),
                            mdpLevelCalculation.getOutSpinner(),
                                findViewById(R.id.dpContainer),
                                dpPresenter.getLevelCalcCase(
                                    mdpLevelCalculation.getEditTextIds(),
                                    val, mdpLevelCalculation.getInSpinner(), findViewById(R.id.dpContainer)
                            )
                    );
                        break;
                    case 1:
                        editTextIds=mppsLevelCalculation.getEditTextIds();
                        len=editTextIds.length;
                        val=new String[len];

                        for(int i=0;i<len;i++){
                            val[i] = "0";
                        }

                        dpPresenter.setResult(mppsLevelCalculation.getOutTextView(),
                            mppsLevelCalculation.getOutSpinner(), findViewById(R.id.ppsContainer),
                            dpPresenter.getLevelCalcCase(
                                    mppsLevelCalculation.getEditTextIds(),
                                    val, mppsLevelCalculation.getInSpinner(), findViewById(R.id.ppsContainer)
                            )
                    );
                        break;
                    case 2:
                        editTextIds=mpLevelCalculation.getEditTextIds();
                        len=editTextIds.length;
                        val=new String[len];

                        for(int i=0;i<len;i++){
                            val[i] = "0";
                        }

                        dpPresenter.setResult(mpLevelCalculation.getOutTextView(),
                            mpLevelCalculation.getOutSpinner(), findViewById(R.id.pContainer),
                            dpPresenter.getLevelCalcCase(
                                    mpLevelCalculation.getEditTextIds(),
                                    val, mpLevelCalculation.getInSpinner(), findViewById(R.id.pContainer)
                            )
                    );
                        break;
                }
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: currentVariable=position;
                            hidePPsFragment();
                            hidePFragment();
                            showDpFragment();
                            //showDpPsFragment();
                        break;

                    case 1: currentVariable=position;
                            hidePFragment();
                            //hideDpPsFragment();
                            hideDpFragment();
                            showPPsFragment();
                        break;

                    case 2: currentVariable=position;
                            //hideDpPsFragment();
                            hideDpFragment();
                            hidePPsFragment();
                            showPFragment();
                    break;

                  /*
                  case 3: currentVariable=position;
                            hidePPsFragment();
                            hideDpPsFragment();
                            hideDpFragment();
                            showPFragment();
                        break;
                    */
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
/*
            protected boolean isCreatedDpPsFragment()
            {
            return mdppsLevelCalculation!=null;
            }


         protected void showDpPsFragment()
         {
                if(isCreatedDpPsFragment()){
                    FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
                    FT.show(mdppsLevelCalculation).commit();
                }
                else {
                    buildDpPsFragment();
                }
            }

            protected void hideDpPsFragment()
            {
                if(isCreatedDpPsFragment())
                {
                    FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
                    FT.hide(mdppsLevelCalculation).commit();
                }
                else {
                    buildDpPsFragment();
                    hideDpPsFragment();
                }
            }

            protected void buildDpPsFragment()
            {
                mdppsLevelCalculation = new dpPsFragment();
                FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
                FT.replace(R.id.dppsContainer, mdppsLevelCalculation).commit();

            }
*/
    protected boolean isCreatedDpFragment()
    {
        return mdpLevelCalculation!=null;
    }


    protected void showDpFragment(){
        if(isCreatedDpFragment()){
            FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
            FT.show(mdpLevelCalculation).commit();
        }
        else {
            buildDpFragment();
        }
    }

    protected void hideDpFragment(){
        if(isCreatedDpFragment())
        {
            FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
            FT.hide(mdpLevelCalculation).commit();
        }
        else {
            buildDpFragment();
            hideDpFragment();
        }
    }

    protected void buildDpFragment(){
        mdpLevelCalculation = new dpFragment();
        FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
        FT.replace(R.id.dpContainer, mdpLevelCalculation).commit();

    }

    protected boolean isCreatedPPsFragment()
    {
        return mppsLevelCalculation!=null;
    }


    protected void showPPsFragment(){
        if(isCreatedPPsFragment()){
            FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
            FT.show(mppsLevelCalculation).commit();
        }
        else {
            buildPPsFragment();
        }
    }

    protected void hidePPsFragment(){
        if(isCreatedPPsFragment())
        {
            FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
            FT.hide(mppsLevelCalculation).commit();
        }
        else {
            buildPPsFragment();
            hidePPsFragment();
        }
    }

    protected void buildPPsFragment(){
        mppsLevelCalculation = new ppsFragment();
        FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
        FT.replace(R.id.ppsContainer, mppsLevelCalculation).commit();

    }

    protected boolean isCreatedPFragment()
    {
        return mpLevelCalculation!=null;
    }


    protected void showPFragment(){
        if(isCreatedPFragment()){
            FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
            FT.show(mpLevelCalculation).commit();
        }
        else {
            buildPFragment();
        }
    }

    protected void hidePFragment(){
        if(isCreatedPFragment())
        {
            FragmentTransaction FT=getSupportFragmentManager().beginTransaction();
            FT.hide(mpLevelCalculation).commit();
        }
        else {
            buildPFragment();
            hidePFragment();
        }
    }

    protected void buildPFragment(){
        mpLevelCalculation = new pFragment();
        FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
        FT.replace(R.id.pContainer, mpLevelCalculation).commit();

    }

}
