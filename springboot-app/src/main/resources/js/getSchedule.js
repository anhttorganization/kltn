//var a = $.map($('.grid-roll2>table>tbody>tr'), tr => $(tr).find('>td'));
//	var arr = [];
//        a.forEach((tr)=> {
//            var index = arr.length;
//            if (!arr[index]) {
//                arr[index] = [];
//            }
//            $.each(tr, (i, td) => {
//                    arr[index].push($(td).text());  
//            })
//			arr[index] = JSON.stringify(arr[index]);
//        })
//return arr;

var a = $.map($('.grid-roll2>table>tbody>tr'), tr => $(tr).find('>td'));
	var arr = [];
        a.forEach((tr)=> {
            var index = arr.length;
            if (!arr[index]) {
                arr[index] = [];
            }
			var dssv = tr[14].querySelector('a');
            $.each(tr, (i, td) => {
                arr[index].push($(td).text());  
            })
			arr[index].pop();
			arr[index].push(dssv.href);
			//arr[index] = JSON.stringify(arr[index]);
        })
        
return arr;