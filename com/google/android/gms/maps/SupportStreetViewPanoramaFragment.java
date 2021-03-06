package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.internal.zzai;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class SupportStreetViewPanoramaFragment extends Fragment {
    private final zzb zzbox = new zzb(this);

    static class zza implements StreetViewLifecycleDelegate {
        private final Fragment zzaQq;
        private final IStreetViewPanoramaFragmentDelegate zzboi;

        public zza(Fragment fragment, IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate) {
            this.zzboi = (IStreetViewPanoramaFragmentDelegate) zzac.zzw(iStreetViewPanoramaFragmentDelegate);
            this.zzaQq = (Fragment) zzac.zzw(fragment);
        }

        public void getStreetViewPanoramaAsync(final OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            try {
                this.zzboi.getStreetViewPanoramaAsync(new com.google.android.gms.maps.internal.zzaf.zza(this) {
                    public void zza(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) throws RemoteException {
                        onStreetViewPanoramaReadyCallback.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onCreate(Bundle bundle) {
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
            Bundle arguments = this.zzaQq.getArguments();
            if (arguments != null && arguments.containsKey("StreetViewPanoramaOptions")) {
                zzah.zza(bundle, "StreetViewPanoramaOptions", arguments.getParcelable("StreetViewPanoramaOptions"));
            }
            this.zzboi.onCreate(bundle);
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) zze.zzE(this.zzboi.onCreateView(zze.zzA(layoutInflater), zze.zzA(viewGroup), bundle));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroy() {
            try {
                this.zzboi.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            try {
                this.zzboi.onDestroyView();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.zzboi.onInflate(zze.zzA(activity), null, bundle2);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onLowMemory() {
            try {
                this.zzboi.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onPause() {
            try {
                this.zzboi.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onResume() {
            try {
                this.zzboi.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.zzboi.onSaveInstanceState(bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onStart() {
        }

        public void onStop() {
        }
    }

    static class zzb extends com.google.android.gms.dynamic.zza<zza> {
        private Activity mActivity;
        private final Fragment zzaQq;
        protected zzf<zza> zzbnT;
        private final List<OnStreetViewPanoramaReadyCallback> zzbok = new ArrayList();

        zzb(Fragment fragment) {
            this.zzaQq = fragment;
        }

        private void setActivity(Activity activity) {
            this.mActivity = activity;
            zzIL();
        }

        public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            if (zzAY() != null) {
                ((zza) zzAY()).getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
            } else {
                this.zzbok.add(onStreetViewPanoramaReadyCallback);
            }
        }

        public void zzIL() {
            if (this.mActivity != null && this.zzbnT != null && zzAY() == null) {
                try {
                    MapsInitializer.initialize(this.mActivity);
                    this.zzbnT.zza(new zza(this.zzaQq, zzai.zzbq(this.mActivity).zzI(zze.zzA(this.mActivity))));
                    for (OnStreetViewPanoramaReadyCallback streetViewPanoramaAsync : this.zzbok) {
                        ((zza) zzAY()).getStreetViewPanoramaAsync(streetViewPanoramaAsync);
                    }
                    this.zzbok.clear();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }

        protected void zza(zzf<zza> com_google_android_gms_dynamic_zzf_com_google_android_gms_maps_SupportStreetViewPanoramaFragment_zza) {
            this.zzbnT = com_google_android_gms_dynamic_zzf_com_google_android_gms_maps_SupportStreetViewPanoramaFragment_zza;
            zzIL();
        }
    }

    public static SupportStreetViewPanoramaFragment newInstance() {
        return new SupportStreetViewPanoramaFragment();
    }

    public static SupportStreetViewPanoramaFragment newInstance(StreetViewPanoramaOptions streetViewPanoramaOptions) {
        SupportStreetViewPanoramaFragment supportStreetViewPanoramaFragment = new SupportStreetViewPanoramaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("StreetViewPanoramaOptions", streetViewPanoramaOptions);
        supportStreetViewPanoramaFragment.setArguments(bundle);
        return supportStreetViewPanoramaFragment;
    }

    public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        zzac.zzdn("getStreetViewPanoramaAsync() must be called on the main thread");
        this.zzbox.getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
    }

    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.zzbox.setActivity(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzbox.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzbox.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        this.zzbox.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.zzbox.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        this.zzbox.setActivity(activity);
        this.zzbox.onInflate(activity, new Bundle(), bundle);
    }

    public void onLowMemory() {
        this.zzbox.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.zzbox.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.zzbox.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.zzbox.onSaveInstanceState(bundle);
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }
}
