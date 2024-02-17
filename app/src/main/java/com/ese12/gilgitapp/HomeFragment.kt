package com.ese12.gilgitapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.ese12.gilgitapp.domain.models.BuyerRequestModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var carsRecyclerView: RecyclerView
    lateinit var bikesRecyclerView: RecyclerView
    lateinit var imageSlider: ImageSlider
    lateinit var list: ArrayList<BuyerRequestModel>
    lateinit var carsRecyclerViewList: ArrayList<CarsSuvsModel>
    lateinit var bikesRecyclerViewList: ArrayList<BikeModel>
    lateinit var mobilesRecyclerView: RecyclerView
    lateinit var mobilesList: ArrayList<MobileModels>

    lateinit var laptopsRecyclerView: RecyclerView
    lateinit var laptopList: ArrayList<LaptopsModel>

    lateinit var houseRecyclerView: RecyclerView
    lateinit var houseList: ArrayList<HouseModel>

    lateinit var petsRecyclerView: RecyclerView
    lateinit var petsList: ArrayList<PetsModel>

    lateinit var officeRecyclerView: RecyclerView
    lateinit var officeList: ArrayList<OfficeModel>

    lateinit var shopRecyclerView: RecyclerView
    lateinit var shopList: ArrayList<ShopModel>

    lateinit var textView: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var textView4: TextView
    lateinit var textView5: TextView
    lateinit var textView6: TextView
    lateinit var textView7: TextView
    lateinit var textView8: TextView
    lateinit var textView9: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.buyerRecyclerView)
        carsRecyclerView = view.findViewById(R.id.carsRecyclerView)
        bikesRecyclerView = view.findViewById(R.id.bikesRecyclerView)
        mobilesRecyclerView = view.findViewById(R.id.mobileRecyclerView)
        laptopsRecyclerView = view.findViewById(R.id.laptopRecyeclerView)
        houseRecyclerView = view.findViewById(R.id.houseRecyclerView)
        petsRecyclerView = view.findViewById(R.id.petsRecyclerView)
        officeRecyclerView = view.findViewById(R.id.officeRecyclerView)
        shopRecyclerView = view.findViewById(R.id.shopRecyclerView)

        textView = view.findViewById(R.id.textView)
        textView2 = view.findViewById(R.id.textView2)
        textView3 = view.findViewById(R.id.textView3)
        textView4 = view.findViewById(R.id.textView4)
        textView5 = view.findViewById(R.id.textView5)
        textView6 = view.findViewById(R.id.textView6)
        textView7 = view.findViewById(R.id.textView7)
        textView8 = view.findViewById(R.id.textView8)
        textView9 = view.findViewById(R.id.textView9)

        imageSlider = view.findViewById(R.id.imageSlider)

        val imageListForSlider = ArrayList<SlideModel>().apply {
            add(SlideModel("https://firebasestorage.googleapis.com/v0/b/online-bussines-6e1c9.appspot.com/o/user1%2Fimages%2F1703828687622.jpg?alt=media&token=5c58ed5b-2c57-4914-9162-6e421fa510ab", ""))
            add(SlideModel("https://firebasestorage.googleapis.com/v0/b/online-bussines-6e1c9.appspot.com/o/user1%2Fimages%2F1703828719387.jpg?alt=media&token=7cc8ca87-8e81-4ee1-9b6f-74a372361755", ""))
            add(SlideModel("https://firebasestorage.googleapis.com/v0/b/online-bussines-6e1c9.appspot.com/o/user1%2Fimages%2F1703828732087.jpg?alt=media&token=68d580bb-61d9-4218-a1a3-9dd1e6ecd2f9", ""))
            add(SlideModel("https://media-cldnry.s-nbcnews.com/image/upload/newscms/2021_07/3451045/210218-product-of-the-year-2x1-cs.jpg", ""))
        }

//        val imageListForSlider = arrayListOf(
//            SlideModel("https://i.insider.com/63a0ad5c0675db0018b3765b?width=700"),
//            SlideModel("https://media-cldnry.s-nbcnews.com/image/upload/newscms/2021_07/3451045/210218-product-of-the-year-2x1-cs.jpg"),
//            SlideModel("https://images.moneycontrol.com/static-mcnews/2019/07/Amul-770x435.jpg?impolicy=website&width=770&height=431")
//        )

        imageSlider.setImageList(imageListForSlider, ScaleTypes.FIT)
        carsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bikesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mobilesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        laptopsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        houseRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        petsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        officeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        shopRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        list = ArrayList()
        carsRecyclerViewList = ArrayList()
        bikesRecyclerViewList = ArrayList()
        mobilesList = ArrayList()
        laptopList = ArrayList()
        houseList = ArrayList()
        petsList = ArrayList()
        officeList = ArrayList()
        shopList = ArrayList()

        if (FirebaseAuth.getInstance().currentUser == null) {

        } else {
            var uid = FirebaseAuth.getInstance().currentUser!!.uid
            FirebaseDatabase.getInstance().getReference("Buyer Requests").child(uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        list.clear()
                        for (snap in snapshot.children) {
                            val model = snap.getValue(BuyerRequestModel::class.java)
                            list.add(model!!)
                            textView.visibility = View.VISIBLE
                            list.reverse()
                        }
                        if (isAdded) {
                            recyclerView.adapter = BuyerRequestsAdapter(context!!, list)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })

            FirebaseDatabase.getInstance().getReference("Cars")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        carsRecyclerViewList.clear()
                        for (snap in snapshot.children) {
                            val model = snap.getValue(CarsSuvsModel::class.java)
                            carsRecyclerViewList.add(model!!)
                            textView2.visibility = View.VISIBLE
                            carsRecyclerViewList.reverse()
                        }
                        if (isAdded) {
                            carsRecyclerView.adapter =
                                CarsSuvsAdapter(context!!, carsRecyclerViewList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })

            FirebaseDatabase.getInstance().getReference("Bikes")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        bikesRecyclerViewList.clear()
                        for (snap in snapshot.children) {
                            val model = snap.getValue(BikeModel::class.java)
                            bikesRecyclerViewList.add(model!!)
                            textView3.visibility = View.VISIBLE
                            bikesRecyclerViewList.reverse()
                        }
                        if (isAdded) {
                            bikesRecyclerView.adapter =
                                BikesAdapter(context!!, bikesRecyclerViewList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })

            FirebaseDatabase.getInstance().getReference("Mobiles")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        mobilesList.clear()
                        for (snap in snapshot.children) {
                            val model = snap.getValue(MobileModels::class.java)
                            mobilesList.add(model!!)
                            textView4.visibility = View.VISIBLE
                            mobilesList.reverse()
                        }
                        if (isAdded) {
                            mobilesRecyclerView.adapter = MobilesAdapter(context!!, mobilesList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })


            FirebaseDatabase.getInstance().getReference("Laptops")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        laptopList.clear()
                        for (snap in snapshot.children) {
                            val model = snap.getValue(LaptopsModel::class.java)
                            laptopList.add(model!!)
                            textView5.visibility = View.VISIBLE
                            laptopList.reverse()
                        }
                        if (isAdded) {
                            laptopsRecyclerView.adapter = LaptopsAdapter(context!!, laptopList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }


        FirebaseDatabase.getInstance().getReference("House")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    houseList.clear()
                    for (snap in snapshot.children) {
                        val model = snap.getValue(HouseModel::class.java)
                        houseList.add(model!!)
                        textView6.visibility = View.VISIBLE
                        houseList.reverse()
                    }
                    if (isAdded) {
                        houseRecyclerView.adapter = HouseAdapter(context!!, houseList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        FirebaseDatabase.getInstance().getReference("Pets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    petsList.clear()
                    for (snap in snapshot.children) {
                        val model = snap.getValue(PetsModel::class.java)
                        petsList.add(model!!)
                        textView7.visibility = View.VISIBLE
                        petsList.reverse()
                    }
                    if (isAdded) {
                        petsRecyclerView.adapter = PetsAdapter(context!!, petsList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        FirebaseDatabase.getInstance().getReference("Office")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    officeList.clear()
                    for (snap in snapshot.children) {
                        val model = snap.getValue(OfficeModel::class.java)
                        officeList.add(model!!)
                        textView8.visibility = View.VISIBLE
                        officeList.reverse()
                    }
                    if (isAdded) {
                        officeRecyclerView.adapter = OfficeAdapter(context!!, officeList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        FirebaseDatabase.getInstance().getReference("Shop")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    shopList.clear()
                    for (snap in snapshot.children) {
                        val model = snap.getValue(ShopModel::class.java)
                        shopList.add(model!!)
                        textView9.visibility = View.VISIBLE
                        shopList.reverse()
                    }
                    if (isAdded) {
                        shopRecyclerView.adapter = ShopAdapter(context!!, shopList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        return view
    }
}