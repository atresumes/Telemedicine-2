package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzary implements Creator<zzarx> {
    static void zza(zzarx com_google_android_gms_internal_zzarx, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_internal_zzarx.zzbkr);
        zzc.zza(parcel, 2, com_google_android_gms_internal_zzarx.zzbks, i, false);
        zzc.zza(parcel, 3, com_google_android_gms_internal_zzarx.zzHD(), false);
        zzc.zza(parcel, 4, com_google_android_gms_internal_zzarx.mPendingIntent, i, false);
        zzc.zza(parcel, 5, com_google_android_gms_internal_zzarx.zzHE(), false);
        zzc.zza(parcel, 6, com_google_android_gms_internal_zzarx.zzHF(), false);
        zzc.zzc(parcel, 1000, com_google_android_gms_internal_zzarx.getVersionCode());
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzgS(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzkr(i);
    }

    public zzarx zzgS(Parcel parcel) {
        IBinder iBinder = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        int i2 = 1;
        IBinder iBinder2 = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder3 = null;
        zzarv com_google_android_gms_internal_zzarv = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    com_google_android_gms_internal_zzarv = (zzarv) zzb.zza(parcel, zzaT, zzarv.CREATOR);
                    break;
                case 3:
                    iBinder3 = zzb.zzr(parcel, zzaT);
                    break;
                case 4:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, zzaT, PendingIntent.CREATOR);
                    break;
                case 5:
                    iBinder2 = zzb.zzr(parcel, zzaT);
                    break;
                case 6:
                    iBinder = zzb.zzr(parcel, zzaT);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzarx(i, i2, com_google_android_gms_internal_zzarv, iBinder3, pendingIntent, iBinder2, iBinder);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzarx[] zzkr(int i) {
        return new zzarx[i];
    }
}
