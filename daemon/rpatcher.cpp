//
// Created by PCAditiaID29 on 1/29/2021.
//

#include "rpatcher.h"
#include "INCLUDE/Offsets.h"

struct Feature {
    int SmallCrosshair = 2;
    int LessRecoil = 1;
    int CharacterView = 3;
    int AimBot100M = 4;
} Feature;

void InitMain(int NoFeature, int NoKey);

int main(int argc, char*argv[]){
    int GameCode = atoi(argv[1]);
    int NoFeature = atoi(argv[2]);
    int NoKey = atoi(argv[3]);
    Mem.InitMemory(GameCode);
    InitMain(NoFeature, NoKey);
    return 0;
}


void InitMain(int NoFeature, int NoKey){
    /** NoKey 0 Disable, 1 Enable**/
    if (NoFeature == Feature.SmallCrosshair){
        switch (NoKey) {
            case 0:
                Mem.Write(Mem.getRealOffset(Offsets::SmallCrosshair), OBFUSCATE("-299890175"), TYPE_DWORD);
                break;
            case 1:
                Mem.Write(Mem.getRealOffset(Offsets::SmallCrosshair), OBFUSCATE("0"), TYPE_DWORD);
                break;
        }
    }
    if (NoFeature == Feature.LessRecoil){
        switch (NoKey) {
            case 0:
                Mem.Write(Mem.getRealOffset(Offsets::LessRecoil), OBFUSCATE("-290186560"), TYPE_DWORD);
                break;
            case 1:
                Mem.Write(Mem.getRealOffset(Offsets::LessRecoil), OBFUSCATE("0"), TYPE_DWORD);
                break;
        }
    }
    /** For Character View NoKey is used for NoValue **/
    if (NoFeature == Feature.CharacterView){
        char view[800];
        sprintf(view, OBFUSCATE("%d"), NoKey);
        Mem.Write(Mem.getRealOffset(Offsets::CharacterView), view, TYPE_FLOAT);
    }
    if (NoFeature == Feature.AimBot100M){
        switch (NoKey) {
            case 0:
                Mem.Write(Mem.getRealOffset(Offsets::Aimbot100M), OBFUSCATE("-290174237"), TYPE_DWORD);
                break;
            case 1:
                Mem.Write(Mem.getRealOffset(Offsets::Aimbot100M), OBFUSCATE("0"), TYPE_DWORD);
        }
    }
}