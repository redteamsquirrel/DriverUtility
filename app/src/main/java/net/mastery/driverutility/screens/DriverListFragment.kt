package net.mastery.driverutility.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import net.mastery.driverutility.R
import net.mastery.driverutility.application.BaseFragment
import net.mastery.driverutility.databinding.DriverListFragmentBinding
import net.mastery.driverutility.models.Driver
import net.mastery.driverutility.models.User
import net.mastery.driverutility.services.EndpointInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DriverListFragment : BaseFragment() {

    override val binding get() = _binding as DriverListFragmentBinding

    private val driverListClickListener: DriverListAdapter.OnItemClickListener
    init {
        driverListClickListener = object : DriverListAdapter.OnItemClickListener {
            override fun onItemClick(driver: Driver?) {
                displayDriverDetails(driver)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DriverListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getParcelable<User>(USER_ARG_KEY)
        binding.userWelcome.text = String.format(getString(R.string.welcome), user?.first ?: "")
    }

    override fun onResume() {
        super.onResume()

        binding.driverList.layoutManager = LinearLayoutManager(baseActivity)

        val endpointInterface = EndpointInterface.create().getDrivers()
        with(endpointInterface) {
            enqueue( object : Callback<List<Driver>> {
                override fun onResponse(
                    call: Call<List<Driver>>,
                    response: Response<List<Driver>>
                ) {
                    response.body()?.let { drivers ->
                        binding.driverList.adapter = DriverListAdapter(drivers, driverListClickListener)
                    }
                }

                override fun onFailure(call: Call<List<Driver>>, t: Throwable) {
                    // intentionally unimplemented to limit scope
                }
            })
        }
    }

    private fun displayDriverDetails(driver: Driver?) {
        if (driver != null) {
            val values =
                String.format("Trailer Type:\n %s", driver.details?.trailerType ?: "none") + "\n\n" +
                        String.format("Trailer Length:\n %s", driver.details?.trailerLength ?: 0) + "\n\n" +
                        String.format("Trailer Height:\n %s", driver.details?.trailerHeight ?: 0) + "\n\n" +
                        String.format("Trailer Width:\n %s", driver.details?.trailerWidth ?: 0) + "\n\n" +
                        String.format("Trailer Plate:\n %s", driver.details?.plateNumber ?: 0) + "\n\n" +
                        "Trailer Location:\n ${driver.details?.currentLocation?.latitude}, ${driver.details?.currentLocation?.longitude}"

            showDebugInfoDialog("Driver Details", values)
        }
    }

    private fun showDebugInfoDialog(title: String, message: String?) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_driver_info, null)

        val infoTitle = view.findViewById<TextView>(R.id.dialog_driver_info_title)
        infoTitle.text = title

        val infoContent = view.findViewById<TextView>(R.id.dialog_driver_info_content)
        infoContent.text = message ?: ""

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }
}