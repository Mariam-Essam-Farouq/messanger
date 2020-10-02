package messenger.iti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import messenger.iti.fragments.ContactsFragment;
import messenger.iti.fragments.GroupsFragment;
import messenger.iti.fragments.MessageFragment;
import messenger.iti.fragments.WaitingMessageFragment;

public class MainActivity extends AppCompatActivity {
   private ChipNavigationBar chipNavigationBar;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        }
        if(savedInstanceState==null) {
            chipNavigationBar.setItemSelected(R.id.item1,true);
            fragmentManager = getSupportFragmentManager();
            MessageFragment messageFragment = new MessageFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container , messageFragment)
                    .commit();
        }

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                 Fragment fragment=null;
                switch (i) {
                    case R.id.item1:
                        fragment = new MessageFragment();
                        break;
                    case R.id.item2:
                        fragment = new WaitingMessageFragment();
                        break;
                    case R.id.item3:
                        fragment = new ContactsFragment();
                        break;
                    case R.id.item4:
                        fragment = new GroupsFragment();
                        break;
                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                } else {
                    Toast.makeText(MainActivity.this, "error!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
            public void initComponent() {
                chipNavigationBar = findViewById(R.id.menu_Navigation);
            }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
        }
