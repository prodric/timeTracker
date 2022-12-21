import 'package:flutter/material.dart';
import 'package:intl/intl.dart';


class PageReport extends StatefulWidget {
  const PageReport({Key? key}) : super(key: key);

  @override
  State<PageReport> createState() => _PageReportState();
}

class _PageReportState extends State<PageReport> {
  final List<String> items = [
    'Last week',
    'This week',
    'Yesterday',
    'Today',
    'Other',
  ];
  final List<String> items2 = [
    'Brief',
    'Detailed',
    'Statistic',
  ];
  final List<String> items3 = [
    'Web page',
    'PDF',
    'Text',
  ];
  String? selectedValue;
  String? selectedValue2;
  String? selectedValue3;
  static DateTime today = DateTime.now();
  static DateTime yesterday = yesterday = today.subtract(Duration(days:1));
  static DateTime mondayThisWeek = DateTime(today.year, today.month,
      today.day - today.weekday + 1);
  static DateTime sundayThisWeek = DateTime(today.year, today.month,
      today.day - today.weekday + 7);
  static DateTime initialDate = mondayThisWeek.subtract(new Duration(days:7));
  static DateTime finalDate = mondayThisWeek.subtract(new Duration(days:1));
  late DateTimeRange picker;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    picker = DateTimeRange(start: initialDate, end: finalDate);
  }
  void updatePeriod() {
    if (selectedValue == items[0]) {
      setState(() {
        picker = DateTimeRange(start: initialDate, end: finalDate);
      });
    }
    if (selectedValue == items[1]) {
      setState(() {
        picker = DateTimeRange(start: mondayThisWeek, end: sundayThisWeek);
      });
    }
    if(selectedValue == items[2]){
        setState(() {
          picker = DateTimeRange(start: yesterday, end: yesterday);
        });
      }
    if(selectedValue == items[3]){
      setState(() {
        picker = DateTimeRange(start: today, end: today);
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Report'),
        ),
        body: Center(
          child: Column(
            children:[ Row(
              children:[
                Container(
                  width:100,
                child: const Text('Period',
                  textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 22,
                    ),
                ),
                ),
                DropdownButtonHideUnderline(
                child: DropdownButton(
                  hint: Text(items[0],
                  style: TextStyle(
                    fontSize: 20,
                    color: Theme.of(context).hintColor,
                  ),
                ),
                  items: items.map((item) => DropdownMenuItem<String>(
                    value: item,
                    child: Text(
                      item,
                      style: const TextStyle(
                      fontSize: 20,
                      ),
                    ),
                    ))
                      .toList(),
                  value: selectedValue,
                  onChanged: (value) {
                  setState(() {
                  selectedValue = value as String;
                  updatePeriod();
                  });
                  },

                ),
              ),
          ],
    ),
              Row(
                children:[ Container(
                  width:100,
                  child: const Text('From',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 22,
                    ),
                  ),
                ),

                Text(DateFormat('yyyy:MM:dd').format(picker.start)),
                IconButton(icon: Icon(Icons.calendar_month_sharp),
                  onPressed: () {
                      _pickFromDate();
                  },
                ),
              ],
              ),
              Row(
                children:[ Container(
                  width:100,
                  child: const Text('To',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 22,
                    ),
                  ),
                ),

                  Text(DateFormat('yyyy:MM:dd').format(picker.end)),
                  IconButton(icon: Icon(Icons.calendar_month_sharp),
                    onPressed: () {
                      _pickFromDate();
                    },
                  ),
                ],
              ),
              Row(
                children:[
                  Container(
                    width:100,
                    child: const Text('Content',
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        fontSize: 22,
                      ),
                    ),
                  ),
                  DropdownButtonHideUnderline(
                    child: DropdownButton(
                      hint: Text(items2[0],
                        style: TextStyle(
                          fontSize: 20,
                          color: Theme.of(context).hintColor,
                        ),
                      ),
                      items: items2.map((item) => DropdownMenuItem<String>(
                        value: item,
                        child: Text(
                          item,
                          style: const TextStyle(
                            fontSize: 20,
                          ),
                        ),
                      ))
                          .toList(),
                      value: selectedValue2,
                      onChanged: (value) {
                        setState(() {
                          selectedValue2 = value as String;
                        });
                      },

                    ),
                  ),
                ],
              ),
              Row(
                children:[
                  Container(
                    width:100,
                    child: const Text('Format',
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        fontSize: 22,
                      ),
                    ),
                  ),
                  DropdownButtonHideUnderline(
                    child: DropdownButton(
                      hint: Text(items3[0],
                        style: TextStyle(
                          fontSize: 20,
                          color: Theme.of(context).hintColor,
                        ),
                      ),
                      items: items3.map((item) => DropdownMenuItem<String>(
                        value: item,
                        child: Text(
                          item,
                          style: const TextStyle(
                            fontSize: 20,
                          ),
                        ),
                      ))
                          .toList(),
                      value: selectedValue3,
                      onChanged: (value) {
                        setState(() {
                          selectedValue3 = value as String;
                        });
                      },

                    ),
                  ),
                ],
              ),
              Row(children:[
                Container(
                  width:100,
                  child: TextButton(
                    style: TextButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20, ),
                    ),
                    onPressed: () {},
                    child: const Text('Generate'),
                  ),
                ),
              ],
              ),
        ],
        ),
        ),
    );
  }
  _pickFromDate() async {

      DateTime? newStart = await showDatePicker(
        context: context,
        firstDate: DateTime(picker.start.year - 5),
        lastDate: DateTime(picker.start.year + 5),
        initialDate: picker.start,
      );
      late DateTime end;
    if (newStart !=null) {
       end = DateTime(
          picker.start.year, picker.start.month, picker.start.day + 7); // the present To date
    }
    if (end.difference(newStart!) >= Duration(days: 0)) {
      picker = DateTimeRange(start: newStart, end: end);
// x is where you store the (From,To) DateTime pairs
// associated to the ’Other’ option
      setState(() {
        selectedValue = "Other"; // to redraw the screen
      });
       } else {
        _showAlertDates();
        }
    }
  TextButton _showAlertDates() {
    return TextButton(
      onPressed: () => showDialog<String>(
        context: context,
        builder: (BuildContext context) => AlertDialog(
          title: const Text('Range dates'),
          content: const Text('The From date is after the To date'
              'Please, select a new date.'),
          actions: <Widget>[
            TextButton(
              onPressed: () => Navigator.pop(context, 'OK'),
              child: const Text('ACCEPT'),
            ),
          ],
        ),
      ),
      child: const Text('Show Dialog'),
    );
  }
}


