//
// Created by PCAditiaID29 on 1/29/2021.
//

#include "MemoryPatch.h"


void MemoryPatch::InitMemory(int version) {
    system(OBFUSCATE("echo 8192 > /proc/sys/fs/inotify/max_user_watches"));
    system(OBFUSCATE("echo 8192 > /proc/sys/fs/inotify/max_queued_events"));
    system(OBFUSCATE("echo 8192 > /proc/sys/fs/inotify/max_user_instances"));
    switch (version) {
        case 1:
            Data.processName = OBFUSCATE("com.tencent.ig");
            break;
        case 2:
            Data.processName = OBFUSCATE("com.vng.pubgmobile");
            break;
        case 3:
            Data.processName = OBFUSCATE("com.pubg.krmobile");
            break;
    }
    if (Data.gamePid == 0){
        Data.gamePid = find_pid(Data.processName);
        if (Data.gamePid == 0){
            LOGE("Can't get game pid!");
            return;
        }
    }
    if (Data.libBase == 0){
        Data.libBase = getModuleBase("libUE4.so");
        if (Data.libBase == 0){
            LOGE("Can't get ue4base address!");
            return;
        }
    }
    if (Data.gameHandle == 0){
        char lj[64];
        sprintf(lj, OBFUSCATE("/proc/%d/mem"), Data.gamePid);
        Data.gameHandle = open(lj, O_RDWR);
        lseek(Data.gameHandle, 0, SEEK_SET);
        if (Data.gameHandle == 0){
            LOGE("Can't get game handle!");
            return;
        }
    }
    LOGI("=====================================");
    LOGI("RPtacher by RAIN CITY GAMING");
    LOGI("Website: raincitygaming.com");
    LOGI("=====================================");
    LOGI("Package name: %s", Data.processName);
    LOGI("Game pid: %d", Data.gamePid);
    LOGI("Lib Base: %x", Data.libBase);
    LOGI("=====================================");
}

pid_t MemoryPatch::find_pid(const char *game_package) {
    int id;
    pid_t pid = -1;
    DIR* dir;
    FILE* fp;
    char filename[32];
    char cmdline[256];

    struct dirent* entry;
    if (game_package == NULL) {
        return -1;
    }
    dir = opendir("/proc");
    if (dir == NULL) {
        return -1;
    }
    while ((entry = readdir(dir)) != NULL) {
        id = atoi(entry->d_name);
        if (id != 0) {
            sprintf(filename, "/proc/%d/cmdline", id);
            fp = fopen(filename, "r");
            if (fp) {
                fgets(cmdline, sizeof(cmdline), fp);
                fclose(fp);

                if (strcmp(game_package, cmdline) == 0) {
                    pid = id;
                    break;
                }
            }
        }
    }

    closedir(dir);
    return pid;
}

RADDR MemoryPatch::getModuleBase(const char *module_name) {
    FILE *fp;
    unsigned int addr = 0;
    char filename[32], buffer[1024];
    snprintf(filename, sizeof(filename), OBFUSCATE("/proc/%d/maps"), Data.gamePid);
    fp = fopen(filename, OBFUSCATE("rt"));
    if (fp != nullptr) {
        while (fgets(buffer, sizeof(buffer), fp)) {
            if (strstr(buffer, module_name)) {
#if defined(__LP64__)
                sscanf(buffer, OBFUSCATE("%lx-%*s"), &addr);
#else
                sscanf(buffer, OBFUSCATE("%x-%*s"), &addr);
#endif
                break;
            }
        }
        fclose(fp);
    }
    return addr;
}

RADDR MemoryPatch::getRealOffset(RADDR offset) {
    if (Data.libBase == 0) {
        return 0;
    }
    return (Data.libBase + offset);
}
void MemoryPatch::Write(RADDR address, const char *value, type TYPE) {

    switch (TYPE) {
        case TYPE_DWORD:
            WriteDword(address, atoi(value));
            break;
        case TYPE_FLOAT:
            WriteFloat(address, atof(value));
            break;
    }
}

void MemoryPatch::WriteDword(RADDR address, DWORD value) {
    pwrite64(Data.gameHandle, &value, sizeof(value), address);
}

void MemoryPatch::WriteFloat(RADDR address, FLOAT value) {
    pwrite64(Data.gameHandle, &value, sizeof(value), address);
}
