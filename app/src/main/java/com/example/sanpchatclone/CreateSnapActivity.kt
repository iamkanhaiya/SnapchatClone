package com.example.sanpchatclone

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*


class CreateSnapActivity : AppCompatActivity() {


    var createSnapImageView : ImageView? = null
    var messageEditText: EditText?= null
    val imageName = UUID.randomUUID().toString() + ".jpg"
    var imagestatus: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        createSnapImageView = findViewById(R.id.imageView2)
        messageEditText = findViewById(R.id.mymessage)
        imagestatus = findViewById(R.id.forstatus)
    }

     fun getPhoto(){

         val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         startActivityForResult(intent,1)

     }



    fun chooseImageClicked(view: View){
         if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

             requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)


         }else {


             getPhoto()

         }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImage = data!!.data

        if(requestCode==1 && resultCode ==Activity.RESULT_OK && data!= null){

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                createSnapImageView?.setImageBitmap(bitmap)
            } catch(e: Exception){


                e.printStackTrace()
            }


        }




    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==1) {
            if (grantResults.size > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                getPhoto()


            }

        }

    }

    fun nextClicked(view: View){

// Get the data from an ImageView as bytes

        // Get the data from an ImageView as bytes
        createSnapImageView?.setDrawingCacheEnabled(true)
        createSnapImageView?.buildDrawingCache()
        val bitmap = (createSnapImageView?.getDrawable() as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data= baos.toByteArray()




        val uploadTask: UploadTask =  FirebaseStorage.getInstance().getReference().child("imagess").child(imageName).putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(this,"UploadFailed",Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {



            Log.i("data","done");
            Toast.makeText(this,"Uploaddone",Toast.LENGTH_SHORT).show()


            FirebaseStorage.getInstance().getReference().child("imagess").child(imageName).getDownloadUrl()
                .addOnSuccessListener(OnSuccessListener<Uri?> { downloadUri->

                var myurl = downloadUri.toString()


                    val intent = Intent(this,ChooseUserActivity::class.java);
                    intent.putExtra("imageUrl",myurl)
                    intent.putExtra("imageName",imageName)
                    intent.putExtra("message",messageEditText?.text.toString())
                    startActivity(intent);



            }).addOnFailureListener {
                // Handle any errors
            }

        }
        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
            println("Upload is $progress% done")
        }.addOnPausedListener {
            println("Upload is paused")
        }




    }


 }