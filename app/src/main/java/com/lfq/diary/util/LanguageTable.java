package com.lfq.diary.util;

public class LanguageTable {
    public static String[][] table = new String[][]{
            {"日记","日記","Diary"},
            {"电话","電話","Phone"},
            {"记事本","手帳","Notepad"},
            {"年","年","Year"},
            {"月","月","Month"},
            {"日","日","Day"},
            {"确定删除","削除の確認","Yes, delete."},
            {"取消","キャンセル","cancel"},
            {"确定","確認","ok"},
            {"保存","保存","save"},
            {"无标题","タイトルなし","No title"},
            {"未命名","名前なし","Unnamed"},
            {"你真的要删除这个日记吗?","本当にこの日記を削除しますか？","Do you really want to delete this diary?"},
            {"此操作无法逆转","この操作は逆転できません。","This operation cannot be reversed"},
            {"你确定要删除所有的数据吗?","本当にすべてのデータを削除しますか？","Are you sure you want to delete all data?"},
            {"设置","設定","Set up"},
            {"主题与风格","テーマとスタイル","Theme and Style"},
            {"语言","言語","Language"},
            {"设置密码","パスワードの設定","Set password"},
            {"备份","バックアップ","Backups"},
            {"导入","インポート","Import"},
            {"导出","エクスポート","Export"},
            {"点击选择导入的文件","インポートしたファイルを選択するにはクリックしてください。","Click to select the imported file."},
            {"点击选择导出的目录","エクスポートするディレクトリを選択するにはクリックしてください。","Click to select the exported directory."},
            {"关于","アバウト","About"},
            {"清除数据","データをクリア","Clear data"},
            {"淡蓝","薄い青","Light blue"},
            {"粉红","ピンク","pink"},
            {"设置成功，重新打开后生效","設定に成功しました。再オープンしたら有効です。","Setup is successful and will take effect after reopening."},
            {"新建联系人","新しい連絡先","New contacts"},
            {"备注名","コメント名","Memo name"},
            {"电话号码","電話番号","Phone number"},
            {"联系人","連絡先","Contacts"},
            {"拨号","ダイヤル","Call"},
            {"你确定要删除这个联系人吗？","本当にこの連絡先を削除しますか？","Are you sure you want to delete this contact?"},
            {"导入本地联系人","ローカルの連絡先をインポート","Import local contacts"},
            {"标题","タイトル","Title"},
            {"内容","テキスト","Content"},
            {"你确定要删除这个记事本吗？","本当にこのメモ帳を削除しますか？","Are you sure you want to delete this notepad?"},
            {"请输入密码","パスワードを入力してください","Please input a password."},
            {"请再次输入密码","パスワードをもう一度入力してください。","Please enter your password again."},
            {"密码错误","パスワードエラー","Password error."},
            {"密码正确","パスワードが正しい","Password correct"},
            {"清除密码","パスワードをクリア","Password removal"},
            {"两次输入的密码不一致,请重试","二回入力したパスワードが一致しませんでした。やり直してください。","The passwords entered two times are inconsistent. Please try again."},
            {"设置成功","設定成功","Set up successfully"},
            {"已清除","クリアしました","Cleared"},
            {"新建文件夹","新規フォルダ","New folder"},
            {"创建成功","作成成功","Create success"},
            {"创建失败","作成に失敗しました","Create failure"},
            {"分享到...","共有...","Share to..."},
            {"请输入被导入的数据的密码","導入されたデータのパスワードを入力してください。","Please enter the password of the data being imported"},
    };

    public static String[][] weektable = new String[][]{
            {"星期天","日曜日","Sunday"},
            {"星期一","月曜日","Monday"},
            {"星期二","火曜日","Tuesday"},
            {"星期三","水曜日","Wednesday"},
            {"星期四","木曜日","Thursday"},
            {"星期五","金曜日","Friday"},
            {"星期六","土曜日","Saturday"},
    };

    public static String[][] monthtable = new  String[][]{
            {"一月","1月","January"},
            {"二月","2月","February"},
            {"三月","3月","March"},
            {"四月","4月","April"},
            {"五月","5月","May"},
            {"六月","6月","June"},
            {"七月","7月","July"},
            {"八月","8月","August"},
            {"九月","9月","September"},
            {"十一月","10月","October"},
            {"十二月","11月","November"},
            {"十三月","12月","December"},
    };

    /**
     * 备份数据 导出 提示信息
     */
    public static String[] backupsTips = new String[]{
            "注意：导出数据会连同密码一起导出，请记住自己的密码（如果密码为空的话请忽略）",
            "注意：データはパスワードと一緒にエクスポートされますので、自分のパスワードを覚えてください。",
            "Note: Export data will be exported with password. Please remember your password (if the password is empty, please ignore it)."
    };

    public static String[][] getTable() {
        return table;
    }

    public static String[][] getWeektable() {
        return weektable;
    }

    public static String[][] getMonthtable() {
        return monthtable;
    }

    public static String[] getBackupsTips() {
        return backupsTips;
    }
}
