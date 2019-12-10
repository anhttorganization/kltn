var a = $.map($('#ctl00_ContentPlaceHolder1_ctl00_gvXem>tbody>tr'), tr => $(tr).find('>td'));

var arr = [];
a.forEach((tr)=> {
	if(tr.length > 0)
    var index = arr.length;
	if (!arr[index]) {
        arr[index] = [];
    }
    $.each(tr, (i, td) => {
		
        arr[index].push($(td).text().trim());  
    })
    arr[index] = JSON.stringify(arr[index]);
})

return arr;