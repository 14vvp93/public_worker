package com.hcn.common.app;

import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin.support.utils.ShellUtil;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HShellUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");

    private HShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static HUtilsEx.Task<CommandResult> execCmdAsync(String str, boolean z, HUtilsEx.Consumer<CommandResult> consumer) {
        return execCmdAsync(new String[]{str}, z, true, consumer);
    }

    public static HUtilsEx.Task<CommandResult> execCmdAsync(List<String> list, boolean z, HUtilsEx.Consumer<CommandResult> consumer) {
        return execCmdAsync(list == null ? null : (String[]) list.toArray(new String[0]), z, true, consumer);
    }

    public static HUtilsEx.Task<CommandResult> execCmdAsync(String[] strArr, boolean z, HUtilsEx.Consumer<CommandResult> consumer) {
        return execCmdAsync(strArr, z, true, consumer);
    }

    public static HUtilsEx.Task<CommandResult> execCmdAsync(String str, boolean z, boolean z2, HUtilsEx.Consumer<CommandResult> consumer) {
        return execCmdAsync(new String[]{str}, z, z2, consumer);
    }

    public static HUtilsEx.Task<CommandResult> execCmdAsync(List<String> list, boolean z, boolean z2, HUtilsEx.Consumer<CommandResult> consumer) {
        return execCmdAsync(list == null ? null : (String[]) list.toArray(new String[0]), z, z2, consumer);
    }

    public static HUtilsEx.Task<CommandResult> execCmdAsync(final String[] strArr, final boolean z, final boolean z2, HUtilsEx.Consumer<CommandResult> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<CommandResult>(consumer) { // from class: com.hcn.common.app.HShellUtils.1
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public CommandResult doInBackground() {
                return HShellUtils.execCmd(strArr, z, z2);
            }
        });
    }

    public static CommandResult execCmd(String str, boolean z) {
        return execCmd(new String[]{str}, z, true);
    }

    public static CommandResult execCmd(List<String> list, boolean z) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), z, true);
    }

    public static CommandResult execCmd(String[] strArr, boolean z) {
        return execCmd(strArr, z, true);
    }

    public static CommandResult execCmd(String str, boolean z, boolean z2) {
        return execCmd(new String[]{str}, z, z2);
    }

    public static CommandResult execCmd(List<String> list, boolean z, boolean z2) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), z, z2);
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0141  */
    /* JADX WARN: Code duplicated, block: B:107:0x014a  */
    /* JADX WARN: Code duplicated, block: B:108:0x014c  */
    /* JADX WARN: Code duplicated, block: B:111:0x0153  */
    /* JADX WARN: Code duplicated, block: B:131:0x017c  */
    /* JADX WARN: Code duplicated, block: B:141:0x0172 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:143:0x012d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x0168 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:149:0x0123 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x015e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:159:0x0137 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:174:? A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public static CommandResult execCmd(String[] strArr, boolean z, boolean z2) throws Throwable {
        Process processExec;
        ?? bufferedReader;
        StringBuilder sb;
        ?? bufferedReader2;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        String string;
        ?? r6;
        int iWaitFor = -1;
        if (strArr == null || strArr.length == 0) {
            return new CommandResult(-1, "", "");
        }
        DataOutputStream dataOutputStream = null;
        ?? r3 = 0;
        dataOutputStream = null;
        try {
            processExec = Runtime.getRuntime().exec(z ? ShellUtil.COMMAND_SU : ShellUtil.COMMAND_SH);
            try {
                DataOutputStream dataOutputStream2 = new DataOutputStream(processExec.getOutputStream());
                try {
                    try {
                        for (String str : strArr) {
                            if (str != null) {
                                dataOutputStream2.write(str.getBytes());
                                dataOutputStream2.writeBytes(LINE_SEP);
                                dataOutputStream2.flush();
                            }
                        }
                        dataOutputStream2.writeBytes("exit" + LINE_SEP);
                        dataOutputStream2.flush();
                        iWaitFor = processExec.waitFor();
                        if (z2) {
                            sb3 = new StringBuilder();
                            try {
                                sb4 = new StringBuilder();
                                try {
                                    bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream(), "UTF-8"));
                                    try {
                                        bufferedReader2 = new BufferedReader(new InputStreamReader(processExec.getErrorStream(), "UTF-8"));
                                        try {
                                            String line = bufferedReader.readLine();
                                            if (line != null) {
                                                sb3.append(line);
                                                while (true) {
                                                    String line2 = bufferedReader.readLine();
                                                    if (line2 == null) {
                                                        break;
                                                    }
                                                    sb3.append(LINE_SEP).append(line2);
                                                }
                                            }
                                            String line3 = bufferedReader2.readLine();
                                            if (line3 != null) {
                                                sb4.append(line3);
                                                while (true) {
                                                    String line4 = bufferedReader2.readLine();
                                                    if (line4 == null) {
                                                        break;
                                                    }
                                                    sb4.append(LINE_SEP).append(line4);
                                                }
                                            }
                                            r3 = bufferedReader;
                                            r6 = bufferedReader2;
                                        } catch (Exception e) {
                                            e = e;
                                            dataOutputStream = dataOutputStream2;
                                            sb = sb3;
                                            e = e;
                                            sb2 = sb4;
                                            bufferedReader = bufferedReader;
                                            bufferedReader2 = bufferedReader2;
                                            try {
                                                e.printStackTrace();
                                                if (dataOutputStream != null) {
                                                    try {
                                                        dataOutputStream.close();
                                                    } catch (IOException e2) {
                                                        e2.printStackTrace();
                                                    }
                                                }
                                                if (bufferedReader != 0) {
                                                    try {
                                                        bufferedReader.close();
                                                    } catch (IOException e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                                if (bufferedReader2 != 0) {
                                                    try {
                                                        bufferedReader2.close();
                                                    } catch (IOException e4) {
                                                        e4.printStackTrace();
                                                    }
                                                }
                                                if (processExec != null) {
                                                    processExec.destroy();
                                                }
                                                sb3 = sb;
                                                sb4 = sb2;
                                            } catch (Throwable th) {
                                                th = th;
                                                if (dataOutputStream != null) {
                                                    try {
                                                        dataOutputStream.close();
                                                    } catch (IOException e5) {
                                                        e5.printStackTrace();
                                                    }
                                                }
                                                if (bufferedReader != 0) {
                                                    try {
                                                        bufferedReader.close();
                                                    } catch (IOException e6) {
                                                        e6.printStackTrace();
                                                    }
                                                }
                                                if (bufferedReader2 != 0) {
                                                    try {
                                                        bufferedReader2.close();
                                                    } catch (IOException e7) {
                                                        e7.printStackTrace();
                                                    }
                                                }
                                                if (processExec != null) {
                                                    processExec.destroy();
                                                    throw th;
                                                }
                                                throw th;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            dataOutputStream = dataOutputStream2;
                                            if (dataOutputStream != null) {
                                                dataOutputStream.close();
                                            }
                                            if (bufferedReader != 0) {
                                                bufferedReader.close();
                                            }
                                            if (bufferedReader2 != 0) {
                                                bufferedReader2.close();
                                            }
                                            if (processExec != null) {
                                                processExec.destroy();
                                                throw th;
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e8) {
                                        e = e8;
                                        bufferedReader2 = 0;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        bufferedReader2 = 0;
                                    }
                                } catch (Exception e9) {
                                    e = e9;
                                    bufferedReader = 0;
                                    bufferedReader2 = 0;
                                }
                            } catch (Exception e10) {
                                sb2 = null;
                                bufferedReader = 0;
                                bufferedReader2 = 0;
                                dataOutputStream = dataOutputStream2;
                                sb = sb3;
                                e = e10;
                            }
                        } else {
                            sb3 = null;
                            sb4 = null;
                            r6 = 0;
                        }
                        try {
                            dataOutputStream2.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                        }
                        if (r3 != 0) {
                            try {
                                r3.close();
                            } catch (IOException e12) {
                                e12.printStackTrace();
                            }
                        }
                        if (r6 != 0) {
                            try {
                                r6.close();
                            } catch (IOException e13) {
                                e13.printStackTrace();
                            }
                        }
                        if (processExec != null) {
                            processExec.destroy();
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        bufferedReader = 0;
                        bufferedReader2 = 0;
                    }
                } catch (Exception e14) {
                    e = e14;
                    sb = null;
                    sb2 = null;
                    bufferedReader = 0;
                    bufferedReader2 = 0;
                    dataOutputStream = dataOutputStream2;
                }
            } catch (Exception e15) {
                e = e15;
                sb = null;
                sb2 = sb;
                StringBuilder sb5 = sb2;
                bufferedReader2 = sb5;
                bufferedReader = sb5;
                e.printStackTrace();
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (bufferedReader != 0) {
                    bufferedReader.close();
                }
                if (bufferedReader2 != 0) {
                    bufferedReader2.close();
                }
                if (processExec != null) {
                    processExec.destroy();
                }
                sb3 = sb;
                sb4 = sb2;
                if (sb3 == null) {
                    string = "";
                } else {
                    string = sb3.toString();
                }
                return new CommandResult(iWaitFor, string, sb4 != null ? sb4.toString() : "");
            } catch (Throwable th5) {
                th = th5;
                bufferedReader = 0;
                bufferedReader2 = bufferedReader;
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (bufferedReader != 0) {
                    bufferedReader.close();
                }
                if (bufferedReader2 != 0) {
                    bufferedReader2.close();
                }
                if (processExec != null) {
                    processExec.destroy();
                    throw th;
                }
                throw th;
            }
        } catch (Exception e16) {
            e = e16;
            processExec = null;
            sb = null;
        } catch (Throwable th6) {
            th = th6;
            processExec = null;
            bufferedReader = 0;
        }
        if (sb3 == null) {
            string = "";
        } else {
            string = sb3.toString();
        }
        return new CommandResult(iWaitFor, string, sb4 != null ? sb4.toString() : "");
    }

    public static class CommandResult {
        public String errorMsg;
        public int result;
        public String successMsg;

        public CommandResult(int i, String str, String str2) {
            this.result = i;
            this.successMsg = str;
            this.errorMsg = str2;
        }

        public String toString() {
            return "result: " + this.result + "\nsuccessMsg: " + this.successMsg + "\nerrorMsg: " + this.errorMsg;
        }
    }
}
