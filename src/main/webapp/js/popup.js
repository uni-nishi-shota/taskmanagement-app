/**    
 * 
 */

function confirmFunction(id) {//確認モーダルウィンドウ
    const isConfirmed = confirm("本当に削除しますか？\n削除したタスクは”最近削除した項目”から見れます。\n７日後に自動的に消えます。");
    if (isConfirmed) {
        location.href = "deletetask?id=" + id;
    } else {
        alert("キャンセルしました。");
    }
}


function deleteConfirmFunction(id) {//確認モーダルウィンドウ
    const isConfirmed = confirm("このタスクを完全に削除しますか？\nこの操作は元に戻せません。");
    if (isConfirmed) {
        location.href = "harddeletetask?id=" + id;
    } else {
        alert("キャンセルしました。");
    }
}

/*モーダル版復元
function  restoreConfirmFunction(id) {//確認モーダルウィンドウ
    const isConfirmed = confirm("このタスクを復元しますか？");
    if (isConfirmed) {
        location.href = "taskrestore?id=" + id;
    } else {
        alert("キャンセルしました。");
    }
}
*/

function restoreConfirmFunction(id) {
    window.open(
        "jsp/restorePopup.jsp?id=" + id,
        "restorePopup",
        "width=400,height=250"
    );
}

function restoreTask() {
		const params = new URLSearchParams(window.location.search);
		const id = params.get("id");

		// 親画面に処理させる
		window.opener.location.href = "/TaskPractice/taskrestore?id=" + id;
		
		// ポップアップ閉じる
		window.close();
	}

