//
// Created by PCAditiaID29 on 1/29/2021.
//

#ifndef RPATCHERDAEMON_MEMORYPATCH_H
#define RPATCHERDAEMON_MEMORYPATCH_H

#include "INCLUDE/Log.h"
#include "obfuscate.h"
#include <dirent.h>
#include <fcntl.h>
#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <unistd.h>
#include <sys/uio.h>
#include <sys/syscall.h>

typedef unsigned int RADDR;
typedef int DWORD;
typedef float FLOAT;

enum type {
    TYPE_DWORD,
    TYPE_FLOAT
};

class MemoryPatch {
public:
    struct Data{
        const char *processName = nullptr;
        int gamePid = 0;
        unsigned int libBase = 0;
        int gameHandle = 0;
    } Data;
    void InitMemory(int version);
    pid_t find_pid(const char *game_package);
    RADDR getModuleBase(const char *module_name);
    RADDR getRealOffset(RADDR offset);
    void WriteFloat(RADDR address, FLOAT value);
    void WriteDword(RADDR address, DWORD value);
    void Write(RADDR address, const char *value, type TYPE);
};


#endif //RPATCHERDAEMON_MEMORYPATCH_H
