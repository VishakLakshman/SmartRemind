package com.example.samp

internal object ExpandableListData {

    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()
            val knightsradiant: MutableList<String> =
                ArrayList()
            knightsradiant.add("Windrunner")
            knightsradiant.add("Elsecaller")
            knightsradiant.add("Bondsmith")
            knightsradiant.add("Lightweaver")
            knightsradiant.add("Skybreaker")

            expandableListDetail["KNIGHTS RADIANT"] = knightsradiant

            return expandableListDetail
        }



}